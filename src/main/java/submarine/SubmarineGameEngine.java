package submarine;

import javax.microedition.midlet.MIDlet;

import cn.ohyeah.itvgame.model.GameRanking;
import cn.ohyeah.stb.game.Configurations;
import cn.ohyeah.stb.game.GameCanvasEngine;
import cn.ohyeah.stb.game.Recharge;
import cn.ohyeah.stb.game.SGraphics;
import cn.ohyeah.stb.game.ServiceWrapper;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.PopupText;
import cn.ohyeah.stb.util.DateUtil;
import cn.ohyeah.stb.util.RandomValue;
import cn.ohyeah.stb.key.KeyCode;

public class SubmarineGameEngine extends GameCanvasEngine implements Common{
	public static boolean bate_version = true;
	public static boolean isSupportFavor = false;	
	public static int ScrW = 0;
	public static int ScrH = 0;
	public static SubmarineGameEngine instance = buildGameEngine();
	
	private static SubmarineGameEngine buildGameEngine() {
		return new SubmarineGameEngine(SubmarineMIDlet.getInstance());
	}
	
	private SubmarineGameEngine(MIDlet midlet) {
		super(midlet);
		setRelease(true);
		ScrW = screenWidth;
		ScrH = screenHeight;
	}
	private boolean isTest=false; //�ؿ�����
	private int status; 
	public static int g_status;
	private static int mainIndex, favorIndex;
	public  Role own;
	public  Role boss;
	private int num = 4; //��Ļ����ʾNPC������
	private int bulletInterval = 2;//�ӵ�������(��)
	public DrawGame draw;
	public static Exploder[] exploders = new Exploder[12];
	public static int eIndex;
	public CreateRole createRole;
	public Propety propety;
	//public OwnProp[] props;
	public SaveGameRecord gameRecord;
	public static long sTime=0, eTime=0, time1,time2, slowTime, slowTime2, hideTime, hideTime2;
	public static long bossTime, bossTime2, recordTime, recordTime2, promptTime, promptTime2;
	public static boolean startFlag = true;
	public static long startTime=0, endTime=0, startTime2, endTime2, startTime3, endTime3;
	public static long levelStime=0,levelEtime=0;//�ؿ�ͣ��ʱ��
	private boolean hideFlag = false;	//�Ƿ�Ϊ����״̬
	public static boolean okPressed = false;
	public static boolean hasPropReward, hasPropReward2, hasPropReward3;
	public static boolean protectionFlag=false; //�Ƿ�Ϊ����״̬
	public static int pFlag=-1; // -1���ܱ���, 0������ʱ����״̬, 1û������ʱ����״̬
	public static boolean isHit;	//��������־
	public static boolean dartleFlag = false, dFlag = false; //�����־
	private long dartleTime,dartleTime2;	//����ʱ����
	public static boolean slowFlag = false; //���ٱ�־
	public static boolean bombFlag = false; //��ը��־
	public static long bombTime1, bombTime2; //��ը����ʱ��
	public static int bombX,bombY,bombImgW=50,bombImgH=49;	//��ը������ͱ�ը�Ŀ�͸�
	public static boolean isMenu=false, isMove=true,isBoss5War=false; //isBoss5War��5��BOSSս��־
	public static long revivalTime1=0, revivalTime2=0; //��ͧ����ʱ��
	public static long netTime, netTime2;  //����ס��ʱ��
	public static int currLevel; //�ؿ��ȼ�
	public static int passState = -2; //ͨ�سɹ���ʧ��   -1,ʧ��  0,����  1,ͨ��
	public static long gameBufferStartTime, gameBufferEndTime;
	public static long warnStartTime, warnEndTime; //boss���־���ʱ��
	public static  boolean rechargeState;//, isEnoughMoney;
	private long reviveTime, reviveTime2; //�����޵�ʱ��
	public static long harmTime, harmTime2; //����������޵�ʱ��
	public static boolean reviveFlag, harmFlag;
	public static boolean isPurchase, isPurchase2; //2,3�Ž�ͧ�����־
	private int shopX=0,shopY=0;
	private int mainOrgame=-1; //�̳ǽ��淵��: 0������ ,1:��Ϸ����
	private static boolean isNewGame=true, nextLevel=false;
	private long gameStartTime;
	public static int attainmentId;
	GameRanking[] gameRanking;
	//public static int money;
	public static int recordFlag; //��Ϸ������¼��ʶ
	private boolean isfreshman; 
	private int r;				//��ͼ���ֵ
	
    /*�浵����*/
    public static int lifeNum;
    public static int eatCount;
    public static int eatCount2;
    public static int eatYellow;
    public static int eatRed;
    public static int eatBlue;
    public static int eatBlack;
    public static int eatJisu;
    public static int nonceLife;
    public static int scores;
    public static int scores2;
    public static int limitLife;
    public static int harm;
    public static int submarineStatus;
    public static int gameStatus;
   /* public static int useHidePropNum;
    public static int useLimitBooldPropNum;
    public static int useMedigelPropNum;*/
    public static int medal;
    public static int medal2;
    public static int medal3;
    public static int difficultLevel; //��Ϸ�Ѷȵȼ�0,��    1,��ͨ, 2,����
    public static boolean isOpenDifficult2; //�Ѷ�2�Ƿ���
    public static boolean isOpenDifficult3; //�Ѷ�3�Ƿ���
    
    public static int bossLife;
    public static int bossMapx;
    public static int bossMapy;
	
    
	protected void loop() {
		
		switch (status) {
		case GAME_STATUS_INIT: //��������
			processInit();
			break;
		case GAME_STATUS_MAIN_MENU: //���˵�
		    processMainMenu();
			break;
		case GAME_STATUS_SELECT_SUBMIRINE: //��ͧѡ��
			processSelectSubmirine();
			break;
		case GAME_STATUS_SELECT_LEVEL: //�ؿ�ѡ��
			levelEtime = System.currentTimeMillis()/1000;
			processSelectLevel();
			break;	
		case GAME_STATUS_PLAYING: //��Ϸ��
			processGamePlaying();
			break;
		case GAME_STATUS_PALYING_MENU: //��Ϸ�еĲ˵�
			processGameMenu();
			break;
		case GAME_STATUS_PASS:	//���ػ�ͨ��
			processPassSuccess();
			break;
		case GAME_STATUS_RANKING: //��Ϸ����
			processRankList();
			break;
		case GAME_STATUS_SHOP: //��Ϸ�̳�
		    processShop();
		    break;
		case GAME_STATUS_INTRO: //��Ϸ���
		    processGameIntro();
		    break;
		case GAME_STAUS_NO_RECORD: //û����Ϸ��¼
		    processNoRecord();
		    break;
		case GAME_STATUS_DIFFICULTYLEVEL: //�Ѷ�ѡ��
		    processSelectDifficultyLevel();
		    break;
		case GAME_STATUS_HELP: //���ְ���
		    processHelp();
		    break;
		}
		switch (status) {
		case GAME_STATUS_INIT: 
			showInit(g);
			break;
		case GAME_STATUS_MAIN_MENU:
			showMainMenu(g,mainIndex);
			break;
		case GAME_STATUS_SELECT_SUBMIRINE: 
			showSelectSubmirine(g);
			break;
		case GAME_STATUS_SELECT_LEVEL:
			showSelectLevel(g);
			break;	
		case GAME_STATUS_PLAYING:
			showGamePlaying(g);
			break;
		case GAME_STATUS_PALYING_MENU:
			showGameMenu(g);
			break;
		case GAME_STATUS_PASS:
			showPassSuccess(g, passState);
			break;
		case GAME_STATUS_RANKING:
			showRankList(g);
			break;
		case GAME_STATUS_SHOP:
			showShop(g);
		    break;
		case GAME_STATUS_INTRO:
			showGameIntro(g);
		    break;
		case GAME_STAUS_NO_RECORD:
		    showNoRecord(g);
		    break;
		case GAME_STATUS_DIFFICULTYLEVEL:
		    showSelectDifficultyLevel(g);
		    break;
		case GAME_STATUS_HELP:
		    showHelp(g);
		    break;
		}
	}

	private void processRecharge() {
		//StateRecharge recharge = new StateRecharge(this);
		Recharge recharge = new Recharge(this);
		recharge.recharge();
	}

	private void showGameMenu(SGraphics g) { //��Ϸ�еĲ˵�
		//showGamePlaying(g);
		draw.drawPalyingMenu(g, m_index);
	}
	

	private void showGameIntro(SGraphics g) {
		draw.drawGameIntro(g);
	}
	
	private void showRankList(SGraphics g){
		draw.drawRankList(g, gameRanking);
	}
	
	private void showShop(SGraphics g) {
		draw.drawShop(g, shopX, shopY, propety);
	}
 
	private void showGamePlaying(SGraphics g) {
		if (isDebugMode()) {
			addDebugUserMessage("1-8���ܼ�:");
		}
		draw.drawGamePlaying(g,r);
		showNpc();
		warnEndTime = System.currentTimeMillis()/1000;
		if(boss!=null){
			showBoss();
		}
		if(own.status!=1){
			createRole.showOwn(g,own); //������ս��
			weapon.showLaser(g, own);
			if(pFlag == 0){
				weapon.showEnergyProtection(g, own);
			}else if(pFlag == 1){
				weapon.showEnergyProtection2(g, own);
			}
		}
		weapon.showBomb(g, own);
		weapon.showParaDrop(g);
		weapon.showDartle(g, own.id);
		
		weapon.showBomb2(g, own, currLevel); //�з�Ǳͧ��ͨ����
		weapon.showBossSkill(g);
		weapon.showTorpedo(g);
		weapon.showNet(g,own);
		if(Weapon.isNet){
			weapon.showNetLocation(g, own);
		}
		DrawGame.msgTime2 = System.currentTimeMillis()/1000;
		draw.drawInfo(g, own, limitLife, currLevel, propety);
		Exploder exploder = null;
		for(int i=0;i<exploders.length;i++){
			if(exploders[i] != null){
				exploder = exploders[i];
				exploder.drawExplode(g, draw);
			}
		}
		if(bombFlag){
			draw.drawBurstEffect2(g, bombX, bombY);
		}
	}

	private void showSelectSubmirine(SGraphics g) {
		draw.drawSelectSubmarine(g, selectL, selectR, index, index2, down, confirm, submarineId, isPurchase, isPurchase2);
	}

	private void showSelectLevel(SGraphics g) {
		draw.drawSelectLevel(g, currLevel);
	}

	public void showPassSuccess(SGraphics g, int state){
		draw.drawUserGameInfo(g, state, currLevel, own, passIndex, difficultLevel);
	}
	
	private void showBoss() {
		if(boss.status!=1 && boss.nonceLife > 0){
			createRole.showBoss(own, boss, g, currLevel, difficultLevel);
		}else{
			boss.status=1;
		}
	}

	private void showNpc() {
		createRole.showNpc(g, difficultLevel); //���з�ս��
	}

	private void showMainMenu(SGraphics g, int index) {
		draw.drawMainMenu(g, mainIndex, favorIndex);
	}

	private void showInit(SGraphics g) {
		/*g.setColor(0X000000);
		g.setClip(0, 0, screenW, screenH);
		g.setColor(0Xffffff);
		g.drawString("������,���Ժ�...", 300, 260, TopLeft);*/
	}

	private void showNoRecord(SGraphics g){
		draw.drawNoRecord(g);
	}
	
	private void showSelectDifficultyLevel(SGraphics g){
		draw.drawDiffucltyLevel(g, d_index);
	}
	int helpIndex=0;
	private void showHelp(SGraphics g){
		draw.drawHelp(g, helpIndex);
	}
	
	private void processHelp(){
		if(keyState.contains(KeyCode.OK)){
			keyState.remove(KeyCode.OK);
			if(helpIndex<1){
				helpIndex++;
			}else{
				helpIndex=0;
				g_status = GAME_SUB_STATUS_PLAYING_NPC;
				status = GAME_STATUS_PLAYING;
				nextLevel = false;
				isNewGame = false;
				createRole.loadImage(currLevel);
				weapon.loadImage();
				promptTime = System.currentTimeMillis()/1000;
				draw.clearHelp();
				time1 = System.currentTimeMillis()/100;
				DrawGame.msg = "�ùؿ�Ҫ����Ǳͧ����:"+DrawGame.totalnums[currLevel-1];
				DrawGame.msgTime = System.currentTimeMillis()/1000;
			}
		}
	}
	
	private int d_index;
	private void processSelectDifficultyLevel(){
		if(keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)){
			status = GAME_STATUS_MAIN_MENU;
			mainIndex=0;
			d_index=0;
		}
		if(keyState.containsAndRemove(KeyCode.DOWN)){
			if(isOpenDifficult2 && !isOpenDifficult3){
				d_index = (d_index+1)%2;
			}else if(isOpenDifficult3){
				d_index = (d_index+1)%3;
			}
		}
		if(keyState.containsAndRemove(KeyCode.UP)){
			if(isOpenDifficult2 && !isOpenDifficult3){
				d_index = (d_index+2-1)%2;
			}else if(isOpenDifficult3){
				d_index = (d_index+3-1)%3;
			}
		}
		if(keyState.containsAndRemove(KeyCode.OK)){
			if(d_index==0){
				difficultLevel = 0;
			}else if(d_index==1){
				difficultLevel = 1;
			}else if(d_index==2){
				difficultLevel = 2;
			}
			status = GAME_STATUS_SELECT_SUBMIRINE;
			draw.clearDifficultLevel();
		}
	}
	
	private void processNoRecord(){
		recordTime2 = System.currentTimeMillis()/1000;
		if(recordTime2-recordTime>1 || keyState.contains(KeyCode.OK)){
			if(keyState.contains(KeyCode.OK)){
				keyState.remove(KeyCode.OK);
			}
			status = GAME_STATUS_MAIN_MENU;
			mainIndex=0;
		}
	}
	
	private int m_index=0;
	private void processGameMenu() {
		isMenu = true;
		if(keyState.contains(KeyCode.DOWN)){
			keyState.remove(KeyCode.DOWN);
			m_index = (m_index+1)%3;
		}
		if(keyState.contains(KeyCode.UP)){
			keyState.remove(KeyCode.UP);
			m_index = (m_index+3-1)%3;
		}
		if(keyState.contains(KeyCode.OK)){
			keyState.remove(KeyCode.OK);
			if(m_index==0){
				isMenu=false;
				status=GAME_STATUS_PLAYING;
			}else if(m_index==1){
				isMenu=false;
				status = GAME_STATUS_SHOP;
				mainOrgame=1;
			}else if(m_index==2){
				isMenu=false;
				updateProps();
				status=GAME_STATUS_MAIN_MENU;
				if(own.scores>0){
					gameRecord.saveGameRecord(own, boss, currLevel, 1);
					gameRecord.saveGameAttainment(own);
				}
				clearGamePlaying();
			}
		}
	}
	
	private void processGamePlaying() {
		if (keyState.containsMoveEventAndRemove(KeyCode.LEFT)) {
			moveRole(0);
		}
		else if (keyState.containsMoveEventAndRemove(KeyCode.RIGHT)) {
			moveRole(1);
		}
		/*
		if(keyState.contains(KeyCode.LEFT) || keyState.isDoubleClick(KeyCode.LEFT)){
			moveRole(0);
			if(!keyState.isDoubleClick(KeyCode.LEFT) && !supportKeyReleased){
				keyState.remove(KeyCode.LEFT);
			}
		} else if(keyState.contains(KeyCode.RIGHT) || keyState.isDoubleClick(KeyCode.RIGHT)){
			moveRole(1);
			if(!keyState.isDoubleClick(KeyCode.RIGHT) && !supportKeyReleased){
				keyState.remove(KeyCode.RIGHT);
			}
		}*/ else if(keyState.contains(KeyCode.OK) && own.status!=1){ //��ͨ����
			keyState.remove(KeyCode.OK);
			if(dartleFlag){ //����(����)
				if(!okPressed){
					weapon.createBomb(own, own.id, own.mapx, own.mapy, 2, own.width, own.height);
					dartleTime = System.currentTimeMillis()/100;
					Weapon.bombAmount++; //�����ӵ�����
					dFlag = true;
					
					startTime = System.currentTimeMillis()/1000;
					okPressed = true;
				}
				
			}else{
				if(!okPressed){ //��ͨ����
					weapon.createBomb(own, own.id, own.mapx, own.mapy, 2, own.width, own.height); 
					Weapon.bombAmount++; //�����ӵ�����
					startTime = System.currentTimeMillis()/1000;
					startTime2 = System.currentTimeMillis()/100;
					okPressed = true;
				}
			}
		} else if(keyState.contains(KeyCode.NUM4) && own.status!=1){  //���п�Ͷ(����)
			keyState.remove(KeyCode.NUM4);
			if(!okPressed){
				if(propety.airDropPropNum>0 || isDebugMode()){
					int x = 25;//own.mapx-120;
					int y = -30;//own.mapy+own.height/2;
					for(int i=0;i<5;i++){
						weapon.createParaDrop(own, x, y, 2);
						x += own.width;
					}
					if(!isDebugMode()){
						propety.airDropPropNum--;
					}
					startTime = System.currentTimeMillis()/1000;
					okPressed = true;
				}
			}
		} else if(keyState.contains(KeyCode.NUM5) && own.status!=1){ //����(����)
			keyState.remove(KeyCode.NUM5);
			if(!dartleFlag){
				if(propety.dartlePropNum>0 || isDebugMode()){
					propety.dartlePropNum--;
					dartleFlag=true;
				}
			}
		} else if(keyState.contains(KeyCode.NUM3) && own.status!=1){ //���͵з��ٶ�(����)
			keyState.remove(KeyCode.NUM3);
			if(!slowFlag){
				if(propety.slowPropNum>0 || isDebugMode()){
					slowTime = System.currentTimeMillis()/1000;
					slowFlag = true;
					if(!isDebugMode()){
						propety.slowPropNum--;
					}	
				}
			}
		} else if(keyState.contains(KeyCode.NUM2) && own.status!=1){  //������
			keyState.remove(KeyCode.NUM2);
			if(own.status!=2){
				if(propety.hidePropNum>0 || isDebugMode()){
					if(Propety.useHidePropNum<5){
						hideTime = System.currentTimeMillis()/1000;
						own.status = 2;  //����״̬
						hideFlag = true;
						if(!isDebugMode()){
							propety.hidePropNum--;
							Propety.useHidePropNum++;
						}
					}else{
						DrawGame.msg = "����ʹ���ѳ���5��";
						DrawGame.msgTime = System.currentTimeMillis()/1000;
					}
				}
			}
		} else if(keyState.contains(KeyCode.NUM6) && own.status!=1){  //���⴩͸��
			keyState.remove(KeyCode.NUM6);
			if(!okPressed){
				if(propety.laserPropNum>0 || isDebugMode()){
					weapon.createLaser(own.id, own.mapx, own.mapy, 2, currLevel, difficultLevel);
					startTime3 = System.currentTimeMillis()/1000;
					startTime = System.currentTimeMillis()/1000;
					okPressed = true;
					if(!isDebugMode()){
						propety.laserPropNum--;
					}
				}
			}
		} else if(keyState.contains(KeyCode.NUM1) && own.status!=1){ //����������
			keyState.remove(KeyCode.NUM1);
			if(!okPressed){
				if(!protectionFlag){
					if(propety.energyPropNum>0 || isDebugMode()){
						if(own.status!=2){
							weapon.createEnergyProtection(own.id, own.mapx, own.mapy, 2);
							pFlag = 1;
							protectionFlag = true;
							okPressed = true;
							startTime = System.currentTimeMillis()/1000;
							if(!isDebugMode()){
								propety.energyPropNum--;
							}
						}
					}
				}
			}
		}else if(keyState.contains(KeyCode.NUM8) && own.status!=1){  //����һ��������
			keyState.remove(KeyCode.NUM8);
			if(!okPressed){
				if(propety.limitBooldPropNum>0 || isDebugMode()){
					if(own.lifeNum<5){
						if(Propety.useLimitBooldPropNum>=2){
							DrawGame.msg = "����ʹ�ò��ܳ���2��";
							DrawGame.msgTime = System.currentTimeMillis()/1000;
						}else{
							own.lifeNum++;
							okPressed = true;
							startTime = System.currentTimeMillis()/1000;
							if(!isDebugMode()){
								propety.limitBooldPropNum--;
								Propety.useLimitBooldPropNum++;
							}
						}
					}else{
						DrawGame.msg = "���ܳ���5������";
						DrawGame.msgTime = System.currentTimeMillis()/1000;
					}
				}
			}
		}else if(keyState.contains(KeyCode.NUM7) && own.status!=1){ //��Ѫ
			keyState.remove(KeyCode.NUM7);
			if(!okPressed){
				if(propety.medigelPropNum>0 || isDebugMode()){
					if(Propety.useMedigelPropNum<3){
						if(own.nonceLife<own.limitLife){
							own.nonceLife = own.limitLife;
							okPressed = true;
							startTime = System.currentTimeMillis()/1000;
							if(!isDebugMode()){
								propety.medigelPropNum--;
								Propety.useMedigelPropNum++;
							}
						}
					}else{
						DrawGame.msg = "����ʹ���ѳ���3��";
						DrawGame.msgTime = System.currentTimeMillis()/1000;
					}
				}
			}
		}else if(keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)){ //������Ϸ�еĲ˵�
			status=GAME_STATUS_PALYING_MENU;
		}
		
		dartleTime2 = System.currentTimeMillis()/100;
		if(dartleFlag && dFlag && dartleTime2-dartleTime>3){
			weapon.createBomb(own, own.id, own.mapx, own.mapy, 2, own.width, own.height);
			Weapon.bombAmount++; //�����ӵ�����
			dFlag = false;
		}
		
		slowTime2 = System.currentTimeMillis()/1000;
		if(slowTime2-slowTime>=15){ //���Ƶз�����ʱ��
			slowFlag = false;
		}
		hideTime2 = System.currentTimeMillis()/1000;
		if(hideFlag && hideTime2-hideTime>=15){ //��������ʱ��
			own.status=0;
			hideFlag = false;
		}
		endTime = System.currentTimeMillis()/1000; //
		if(endTime-startTime>=bulletInterval){
			okPressed = false;
		}
		statisticalMedal(); //ͳ��ѫ��
		propsRewardRules(); //���߽���
		if(own.status!=1){
			attacks.bombAttack(boss, own);
			attacks.skillAttack(boss, own);
			attacks.skill2Attack(boss, own);
			attacks.skill3Attack(boss, own);
			if(own.status!=2){
				attacks.bossSkillAttack(boss, own);
				attacks.bossSkillAttack2(boss, own);
				attacks.bossSkillAttack3(boss, own);
			}
		}
		level(); //�ؿ�
		if(own.status==1){
			weapon.lasers.removeAllElements(); //�����󼤹⴩͸����ʧ
			revivalTime2 = System.currentTimeMillis()/1000;
			if(own.lifeNum>0){
				if((revivalTime2-revivalTime1)>=2 ){
					own = createRole.revive();
					own.status=2;
					reviveFlag = true;
					reviveTime = System.currentTimeMillis()/1000;
				}
			} else{
				gameBufferEndTime = System.currentTimeMillis()/1000;
				if((gameBufferEndTime-gameBufferStartTime)>1){
					status = GAME_STATUS_PASS;    //game over
					g_status=GAME_SUB_STATUS_PLAYING_NPC;
					difficultLevel=0;
					passState=-1;
					if(own.scores>0){
						gameRecord.saveGameAttainment(own);
					}
					clearGamePlaying();
				}
			}
		}
		reviveTime2 = System.currentTimeMillis()/1000;
		if(reviveFlag && (reviveTime2-reviveTime)>3){ //�����󸴻��޵�״̬
			reviveFlag = false;
			own.status = 0;
		}
		
		harmTime2 = System.currentTimeMillis()/100;
		if(harmFlag && (harmTime2-harmTime)>5){ //���������޵�״̬(0.5��)
			harmFlag = false;
			own.status = 0;
		}
		//System.out.println("g_status=="+g_status);
		switch (g_status){
		case GAME_SUB_STATUS_PLAYING_NPC:
			createNpc(currLevel, difficultLevel);
			break;
		case GAME_SUB_STATUS_PLAYING_BOSS:
			createBoss();
			if((currLevel==5 && difficultLevel==1) || (difficultLevel==2)){
				//isBoss5War=true;
				createNpc(currLevel, difficultLevel);
			}
			break;
		}
		if(boss!=null && boss.status==1){   
			gameBufferEndTime = System.currentTimeMillis()/1000;
			if((gameBufferEndTime-gameBufferStartTime)>1){
				if(currLevel<5){				//����
					passState=0;
					own.mapx = 185;
					own.nonceLife = own.limitLife;
				}else{     						//ͨ��
					passState=1;
				}
				//isBoss5War = false;
				status=GAME_STATUS_PASS;
				g_status=GAME_SUB_STATUS_PLAYING_NPC;
				dartleFlag = false;
				own.scores += boss.scores;
				own.scores2 += boss.scores;
				eatYellow=own.eatYellow=0;
				eatRed=own.eatRed=0;
				eatBlue=own.eatBlue=0;
				eatBlack=own.eatBlack=0;
				eatJisu=own.eatJisu=0;
				clearGamePlaying();
				if((medal+4*medal2+16*medal3)<=64-4){
					medal2++;
				}
				
				if(own.scores>0){
					gameRecord.saveGameAttainment(own);
				}
				Propety.useHidePropNum=0;
				Propety.useLimitBooldPropNum=0;
				Propety.useMedigelPropNum=0;
			}
		}
	}
	
	/*������Ϸʹ�õĵ���*/
	private void updateProps(){
		/*int[] nums = {propety.slowPropNum,
				propety.laserPropNum,
				propety.medigelPropNum,
				propety.airDropPropNum,
				propety.limitBooldPropNum,
				propety.energyPropNum,
				propety.hidePropNum,
				propety.dartlePropNum
				};
		System.out.println("(Propety.slowPropNum-own.slowPropNum):"+(Propety.slowPropNum-own.slowPropNum));
		System.out.println("(Propety.laserPropNum-own.laserPropNum):"+(Propety.laserPropNum-own.laserPropNum));
		System.out.println("(Propety.medigelPropNum-own.medigelPropNum):"+(Propety.medigelPropNum-own.medigelPropNum));
		System.out.println("(Propety.airDropPropNum-own.airDropPropNum):"+(Propety.airDropPropNum-own.airDropPropNum));
		System.out.println("(Propety.limitBooldPropNum-own.limitBooldPropNum):"+(Propety.limitBooldPropNum-own.limitBooldPropNum));
		System.out.println("(Propety.energyPropNum-own.energyPropNum):"+(Propety.energyPropNum-own.energyPropNum));
		System.out.println("(Propety.hidePropNum-own.hidePropNum):"+(Propety.hidePropNum-own.hidePropNum));
		System.out.println("(Propety.dartlePropNum-own.dartlePropNum):"+(Propety.dartlePropNum-own.dartlePropNum));*/

		propety.saveProp(propety);
	}
	
	/*��Ϸ��һ��*/
	int passIndex=0;
	public void processPassSuccess(){
		if(keyState.contains(KeyCode.OK)){
			keyState.remove(KeyCode.OK);
			if(passState==0){
				g_status=GAME_SUB_STATUS_PLAYING_NPC;
				status = GAME_STATUS_SELECT_LEVEL;
				currLevel=currLevel+1;
				levelStime = System.currentTimeMillis()/1000;
				passState = -2;
				own.harm = 0;
				nextLevel = true;
			}else if(passState==1){
				if(difficultLevel==0){
					isOpenDifficult2=true;
				}
				if(difficultLevel==1){
					isOpenDifficult3=true;
				}
				if(passIndex==0 && DrawGame.isFireOver){
					status = GAME_STATUS_DIFFICULTYLEVEL;
					DrawGame.isFireOver=false;
					isNewGame=true;
					dartleFlag=false;
					medal=0;
					medal2=0;
					medal3=0;
					/*hasPropReward=false;
					hasPropReward2=false;
					hasPropReward3=false;*/
					difficultLevel = 0;
					warnEndTime = System.currentTimeMillis()/1000;
					passState = -2;
				}else if(passIndex==1 && DrawGame.isFireOver){
					status = GAME_STATUS_MAIN_MENU;
					mainIndex=0;
					DrawGame.isFireOver=false;
					passState = -2;
				}
				if(own.scores>0){
					gameRecord.saveGameRecord(own, boss, currLevel, 1);
					gameRecord.saveGameAttainment(own);
				}
			}else if(passState==-1){
				/*status = GAME_STATUS_RANKING;
				ServiceWrapper sw = getServiceWrapper();
				gameRanking =  sw.queryRankingList(0, 10);*/
				if(passIndex==0){ //���¿�ʼ��Ϸ
					status = GAME_STATUS_DIFFICULTYLEVEL;
					isNewGame=true;
					dartleFlag=false;
					medal=0;
					medal2=0;
					medal3=0;
					hasPropReward=false;
					hasPropReward2=false;
					hasPropReward3=false;
					Propety.useHidePropNum=0;
					Propety.useLimitBooldPropNum=0;
					Propety.useMedigelPropNum=0;
					warnEndTime = System.currentTimeMillis()/1000;
					passState = -2;
				}
				if(passIndex==1){ //����������
					status = GAME_STATUS_MAIN_MENU;
					mainIndex=0;
					passState = -2;
				}
			}
			eatCount2=own.eatCount2=0;
			updateProps();
			draw.initData(); 
			draw.clearGamePass();
		}
		if((keyState.contains(KeyCode.LEFT) && DrawGame.isFireOver)
				|| (keyState.contains(KeyCode.LEFT) && passState==-1)){
			keyState.remove(KeyCode.LEFT);
			passIndex=0;
		}
		if(keyState.contains(KeyCode.RIGHT) && DrawGame.isFireOver
				|| (keyState.contains(KeyCode.RIGHT) && passState==-1)){
			keyState.remove(KeyCode.RIGHT);
			passIndex=1;
		}
		
	}
	/*�ؿ�*/
	private void level(){
		if(isDebugMode() || isTest){
			if(currLevel==1 && own.eatCount2>=3){ 
				levelCommon();
			} else if(currLevel==2 && own.eatCount2>=3 ){
				levelCommon();
			} else if(currLevel==3 && own.eatCount2>=3 ){
				levelCommon();
			} else if(currLevel==4 && own.eatCount2>=3 ){
				levelCommon();
			} else if(currLevel==5 && own.eatCount2>=3 ){
				levelCommon();
			}
		}else if(g_status==GAME_SUB_STATUS_PLAYING_NPC){
			if(currLevel==1 && own.eatCount2>=DrawGame.totalnums[currLevel-1]){ 
				levelCommon();
			} else if(currLevel==2 && own.eatCount2>=DrawGame.totalnums[currLevel-1]){
				levelCommon();
			} else if(currLevel==3 && own.eatCount2>=DrawGame.totalnums[currLevel-1]){
				levelCommon();
			} else if(currLevel==4 && own.eatCount2>=DrawGame.totalnums[currLevel-1]){
				levelCommon();
			} else if(currLevel==5 && own.eatCount2>=DrawGame.totalnums[currLevel-1]){
				levelCommon();
			}
		}
	}
	private void levelCommon(){
		g_status = GAME_SUB_STATUS_PLAYING_BOSS;
		//eatCount2=own.eatCount2=0;
		sTime = System.currentTimeMillis()/1000; //����boss�ƶ�
		bossTime = System.currentTimeMillis()/1000;//boss����ʱ��
		warnStartTime = System.currentTimeMillis()/1000; 
	}
	
	public int selectL,selectR,index=0, index2; //index������ѡ�Ľ�ͧ
	public boolean down=false;
	public int confirm; //����ȷ�Ϻͷ���
	public static int submarineId=100; //�û���ͧid
	private void processSelectSubmirine() {
		
		if (keyState.contains(KeyCode.BACK)){ //���ؼ�ֱ���˳�
			keyState.contains(KeyCode.BACK);
			status=GAME_STATUS_DIFFICULTYLEVEL; //�������˵�
			draw.clearSelectSubmarine();
			down = false;
			index=0;
			confirm=0;
			index2=0;
		}
		
		if(keyState.containsAndRemove(KeyCode.NUM0 | KeyCode.BACK)){
			status=GAME_STATUS_DIFFICULTYLEVEL; //�������˵�
			draw.clearSelectSubmarine();
			down = false;
			index=0;
			confirm=0;
			index2=0;
		}
		
		if(keyState.contains(KeyCode.LEFT)){		
			keyState.remove(KeyCode.LEFT);
			if(!down){
				if(index>0){
					index = (index-1)%3;
				}
				index2=0;
			}else{
				confirm=0;
			}
			/*
			if(index==1 || index==2){
				if(propety.queryOwnProp(engineService, 43)!=null){
					isPurchase2 = true;
					System.out.println("isPurchase2:"+isPurchase2);
				}
				if(propety.queryOwnProp(engineService, 42)!=null){
					isPurchase = true;
					System.out.println("isPurchase:"+isPurchase);
				}
			}*/
		}
		if(keyState.contains(KeyCode.RIGHT)){
			keyState.remove(KeyCode.RIGHT);
			if(!down){
				if(index<2){
					index = (index+1)%3;
				}
				index2=1;
			}else{
				confirm=1;
			}
		}
		if(keyState.contains(KeyCode.DOWN)){
			keyState.remove(KeyCode.DOWN);
			down = true;
		}
		if(keyState.contains(KeyCode.UP)){
			keyState.remove(KeyCode.UP);
			down = false;
		}
		if(keyState.contains(KeyCode.OK)){
			keyState.remove(KeyCode.OK);
			if(down){
				if(confirm==0){
					if((submarineId==101 && !isPurchase) || (submarineId==102 && !isPurchase2)){
						propety.purchaseProp(null, submarineId, 0, engineService);
					}else{
						status=GAME_STATUS_SELECT_LEVEL; //����ؿ�ѡ�����
						levelStime = System.currentTimeMillis()/1000;
						draw.clearSelectSubmarine();
					}
				}else{
					status=GAME_STATUS_INIT; //�������˵�
					draw.clearSelectSubmarine();
				}
			}
		}
		
		if(index == 0){
			selectL=1;
			selectR=0;
			submarineId=100;
		}else if(index==1){
			selectL=0;
			selectR=0;
			submarineId=101;
		}else if(index==2){
			selectL=0;
			selectR=1;
			submarineId=102;
		}
	}

	private void processSelectLevel() {
		if(isNewGame){
			own = createRole.createOwn(difficultLevel);
			limitLife = own.limitLife;
			currLevel=1;
		}else if(!nextLevel){ //������Ϸ
			own = createRole.revive();
			/*Propety.useHidePropNum = useHidePropNum;
			Propety.useLimitBooldPropNum = useLimitBooldPropNum;
			Propety.useMedigelPropNum = useMedigelPropNum;*/
		}
		if(levelEtime-levelStime>=2){
			if(!isfreshman){
				if(isNewGame || nextLevel){
					g_status = GAME_SUB_STATUS_PLAYING_NPC;
					status = GAME_STATUS_PLAYING;
					if(isNewGame){
						gameRecord.saveGameRecord(own, boss, currLevel,1); //��ʼ����Ϸ����֮ǰ�Ĵ浵
						isNewGame = false;
					}
					nextLevel = false;
				}else{
					g_status = gameStatus==0?GAME_SUB_STATUS_PLAYING_NPC:gameStatus;
					status = GAME_STATUS_PLAYING;
				}
				createRole.loadImage(currLevel);
				weapon.loadImage();
				promptTime = System.currentTimeMillis()/1000;
				draw.clearSelectLevel();
				time1 = System.currentTimeMillis()/100;
				//propety.queryOwnAllProps();
				r = RandomValue.getRandInt(0, 2);
				DrawGame.msg = "�ùؿ�Ҫ����Ǳͧ����:"+DrawGame.totalnums[currLevel-1];
				DrawGame.msgTime = System.currentTimeMillis()/1000;
			}else{
				status = GAME_STATUS_HELP; //���ְ���
				draw.clearSelectLevel();
				isfreshman = false;
			}
		}
	}

	private void createBoss() { //����BOSS
		if(boss == null){
			boss = createRole.createBoss(currLevel,difficultLevel);
			if(gameStatus==GAME_SUB_STATUS_PLAYING_BOSS){
				boss.nonceLife = bossLife;
				boss.mapx = bossMapx;
				boss.mapy = bossMapy;
				gameStatus=0;
			}
		}
	}

	private void createNpc(int level, int diffLevel) {	//����NPC
		time2 = System.currentTimeMillis()/100;
		if(time2-time1>=(20-diffLevel*5)){
			createRole.createNpc(num, level, diffLevel); 
			time1=System.currentTimeMillis()/100;
		}
	}

	private void processGameIntro() {
		if (keyState.contains(KeyCode.NUM0) || keyState.contains(KeyCode.BACK)) {
			keyState.remove(KeyCode.NUM0);
			keyState.remove(KeyCode.BACK);
			status=GAME_STATUS_MAIN_MENU;
			draw.clearGameIntro();
		}
	}

	private void processRankList(){
		if (keyState.contains(KeyCode.NUM0) || keyState.contains(KeyCode.BACK)) {
			keyState.remove(KeyCode.NUM0);
			keyState.remove(KeyCode.BACK);
			status=GAME_STATUS_MAIN_MENU;
			draw.clearGameRanking();
		}
	}
	
	private void processShop() {
		if (keyState.contains(KeyCode.NUM0) || keyState.contains(KeyCode.BACK)) {
			keyState.remove(KeyCode.NUM0);
			keyState.remove(KeyCode.BACK);
			if(mainOrgame==0){
				status=GAME_STATUS_MAIN_MENU;
			}else if(mainOrgame==1){
				status = GAME_STATUS_PLAYING;
				//props = propety.queryOwnAllProps(engineService);
			}
			shopX=0;shopY=0;
			draw.clearShop();
		}
		if (keyState.contains(KeyCode.UP)) {
			keyState.remove(KeyCode.UP);
			if(shopY>0){
				shopY = shopY-1;
			}
		}
		if (keyState.contains(KeyCode.DOWN)) {
			keyState.remove(KeyCode.DOWN);
			if(shopY<3){
				shopY = shopY+1;
			}
		}
		if (keyState.contains(KeyCode.LEFT)) {
			keyState.remove(KeyCode.LEFT);
			if(shopX>0){
				shopX = shopX-1;
			}
		}
		if (keyState.contains(KeyCode.RIGHT)) {
			keyState.remove(KeyCode.RIGHT);
			if(shopX<2){
				shopX = shopX+1;
			}
		}
		
		if (keyState.contains(KeyCode.OK)) {
			keyState.remove(KeyCode.OK);
			if(shopX==2){ //�����ֵ
				//status = GAME_STATUS_RECHARGE;
				draw.clearShop();
				processRecharge();
			}else{
				propety.purchaseProp(own, shopX, shopY, engineService); //�������
				//propety.queryOwnAllProps(engineService);
				//status = GAME_STATUS_PURCHASING_STATE;
				//convertStatus = 0;
			}
		}
	}
	
//	private void processPurchasePropState(){
//		if (keyState.contains(KeyCode.NUM0)) {
//			keyState.remove(KeyCode.NUM0);
//			if(convertStatus==1){
//				status=GAME_STATUS_SELECT_SUBMIRINE;
//			}else{
//				status=GAME_STATUS_SHOP;
//			}
//			draw.clearPurchaseState();
//		}
//		if (keyState.contains(KeyCode.LEFT)) {
//			keyState.remove(KeyCode.LEFT);
//			purchaseIndex = 0;
//		}
//		if (keyState.contains(KeyCode.RIGHT)) {
//			keyState.remove(KeyCode.RIGHT);
//			purchaseIndex = 1;
//		}
//		if (keyState.contains(KeyCode.OK)) {
//			keyState.remove(KeyCode.OK);
//			if(isEnoughMoney){ //����ɹ�֮�󷵻��̳ǽ���
//				status=GAME_STATUS_SHOP;
//				draw.clearPurchaseState();
//			}else{ //��������ֵ�򷵻��̳ǽ���
//				if(purchaseIndex==0){
//					status = GAME_STATUS_RECHARGE;
//					draw.clearShop();
//				}else{
//					if(convertStatus==1){
//						status=GAME_STATUS_SELECT_SUBMIRINE;
//					}else{
//						status=GAME_STATUS_SHOP;
//					};
//					draw.clearPurchaseState();
//				}
//			}
//		}
//	}
	
	private void processInit() {
		isSupportFavor = Configurations.getInstance().isFavorWayTelcomgd();
		status = GAME_STATUS_MAIN_MENU;
		mainIndex = 0;
		draw = new DrawGame(this); 
		createRole = new CreateRole();
		propety = new Propety(this);
		propety.queryOwnAllProps();
		gameRecord = new SaveGameRecord(this);
		gameStartTime = engineService.getCurrentTime().getTime();
		java.util.Date gst = new java.util.Date(gameStartTime);
		int year = DateUtil.getYear(gst);
		int month = DateUtil.getMonth(gst);
		attainmentId = year*100+(month+1);
		isfreshman = gameRecord.loadGameRecord(1)==0 ?false:true;
		
	}

	private void processMainMenu() {
		if (keyState.contains(KeyCode.UP) && favorIndex==0) {
			keyState.remove(KeyCode.UP);
			mainIndex = (mainIndex + 6 - 1) % 6;
		} else if (keyState.contains(KeyCode.DOWN) && favorIndex==0) {
			keyState.remove(KeyCode.DOWN);
			mainIndex = (mainIndex + 1) % 6;
		} else if (keyState.contains(KeyCode.OK)) {
			keyState.remove(KeyCode.OK);
			processSubMenu();
	    } else if (keyState.contains(KeyCode.RIGHT)) {
	    	keyState.remove(KeyCode.RIGHT);
	    	favorIndex = 1;
	    	
	    } else if (keyState.contains(KeyCode.LEFT)) {
	    	keyState.remove(KeyCode.LEFT);
	    	favorIndex = 0;
	    }
		if (keyState.contains(KeyCode.BACK)){ //���ؼ�ֱ���˳�
			keyState.contains(KeyCode.BACK);
			updateProps(); //���µ���
			exit = true;
			draw.clearMain();
			clearGamePlaying();
			ServiceWrapper sw = getServiceWrapper();
			sw.userQuit();
		}
	}

	public int myRank;
	private void processSubMenu() {
		if(favorIndex==0){
			if (mainIndex == 0) { //����Ϸ
				status = GAME_STATUS_DIFFICULTYLEVEL;
				g_status = GAME_SUB_STATUS_PLAYING_NPC;
				isNewGame=true;
				currLevel=1;
				dartleFlag=false;
				medal=0;
				medal2=0;
				medal3=0;
				/*hasPropReward=false;
				hasPropReward2=false;
				hasPropReward3=false;*/
				difficultLevel = 0;
				Propety.useHidePropNum=0;
				Propety.useLimitBooldPropNum=0;
				Propety.useMedigelPropNum=0;
				warnEndTime = System.currentTimeMillis()/1000;
				draw.clearMain();
			} else if (mainIndex == 1) {// ������Ϸ
				int result = gameRecord.loadGameRecord(1);
				if(result==0 && eatCount > 0){
					status = GAME_STATUS_SELECT_LEVEL;
					levelStime = System.currentTimeMillis()/1000;
					warnEndTime = System.currentTimeMillis()/1000;
					isNewGame=false;
					draw.clearMain();
				}else{
					status = GAME_STAUS_NO_RECORD; //û����Ϸ��¼
					recordTime = System.currentTimeMillis()/1000;
				}
			} else if (mainIndex == 2){ //��Ϸ����
				status = GAME_STATUS_RANKING;
				draw.clearMain();
				ServiceWrapper sw = getServiceWrapper();
				String datas = sw.loadRanking(3);
				gameRanking = sw.loadRanking(datas, 0, 10);
				myRank = sw.getMyRanking(datas);
			} else if (mainIndex == 3) {// ��Ϸ�̳�
				status = GAME_STATUS_SHOP;
				draw.clearMain();
				mainOrgame=0;
			} else if (mainIndex == 4) {// ��Ϸ���
				status = GAME_STATUS_INTRO;
				draw.clearMain();
			} else if (mainIndex == 5) {// �˳�
				updateProps(); //���µ���
				exit = true;
				draw.clearMain();
				clearGamePlaying();
				ServiceWrapper sw = getServiceWrapper();
				sw.userQuit();
			}
			mainIndex=0;
		}else{ //�����ղ�
			ServiceWrapper sw = getServiceWrapper();
			sw.addFavor();
			PopupText pop = UIResource.getInstance().buildDefaultPopupText();
			if(sw.isServiceSuccessful()){
				pop.setText("�ղسɹ�!");
				pop.popup();
			}else{
				pop.setText(sw.getMessage());
				pop.popup();
			}
		}
	}
	
	/*public static boolean hasRank;
	private void queryRanking(){
		ServiceWrapper sw = getServiceWrapper();
		GameRanking grk = null;
		String datas = sw.loadRanking(3);
		String[] data = ConvertUtil.split(datas, "|");
		String[] str = ConvertUtil.split(data[data.length-1], ":");
		int rankNum = 0;
		if(str==null || str.equals("")){
			if(data.length>10){
				gameRanking = new GameRanking[10];
			}else{
				gameRanking = new GameRanking[data.length];
			}
			rankNum = gameRanking.length;
		}else{
			if(data.length-1>10){
				gameRanking = new GameRanking[11];
			}else{
				gameRanking = new GameRanking[data.length];
			}
			rankNum = gameRanking.length-1;
		}
		String[] data2 = null;
		for(int i=0;i<rankNum;i++){
			data2 = ConvertUtil.split(data[i], ",");
			grk = new GameRanking();
			grk.setUserId(data2[0]);
			grk.setScores(Integer.parseInt(data2[2]));
			grk.setRanking(Integer.parseInt(data2[3]));
			gameRanking[i] = grk;
		}
		if(str==null || str[1]==null){
			hasRank = false;
			return;
		}
		String[] myRank = ConvertUtil.split(str[1], ",");
		GameRanking g = new GameRanking();
		g.setUserId(getEngineService().getUserId());
		g.setRanking(Integer.parseInt(myRank[0]));
		g.setScores(Integer.parseInt(myRank[1]));
		gameRanking[rankNum] = g;
		hasRank = true;
	}*/
	
	public void moveRole(int towards) {
		switch (towards) {
		case 0: // ����--����
			own.direction = 0; 
			if(own.mapx >= 0 && isMove){
				if(own.mapx >= own.speed){
					own.mapx -= own.speed;
				} else {
					own.mapx = 0;
				}
			}
			break;
		case 1: // ����--����
			own.direction = 1;
			if((own.mapx+own.width) < gameMapX && isMove){
				if(gameMapX-(own.mapx+own.width) >= own.speed){
					own.mapx += own.speed;
				} else {
					own.mapx += (gameMapX-(own.mapx+own.width));
				}
			}
			break;
		}
	}
	
	/*ѫ�¼���*/
	private void statisticalMedal(){
		/*ÿ100�ּ�һ��С��ѫ��*/
		if((own.scores2>=1000+difficultLevel*500)&&(medal+4*medal2+16*medal3)<=64){
			medal ++;
			own.scores2 -= 1000+difficultLevel*500;
		}
		/*ÿ�ĸ�Сѫ�»���һ������ѫ��*/
		if(medal>=4){
			medal2++;
			medal -= 4;
		}
		/*ÿ�ĸ���ѫ�»���һ����ѫ��*/
		if(medal2>=4){
			medal3++;
			medal2 -= 4;
		}
	/*	medal=3; ����
		medal2=3;
		medal3=3;*/
	}
	
	/*���߽�������*/
	private void propsRewardRules(){
		int num = medal + 4*medal2 +16*medal3;
		
		if(num >= 2 && !hasPropReward){ 
			int ran = RandomValue.getRandInt(0, 3);
			if(ran==1){
				propety.slowPropNum++;
				hasPropReward=true;
				DrawGame.msg = "���һ�����̵���!";
				DrawGame.msgTime = System.currentTimeMillis()/1000;
			}
		}else if(num >= 20 && !hasPropReward2){
			hasPropReward2=true;
			int i = RandomValue.getRandInt(0, 3);
			if(i==0){
				DrawGame.msg = "���һ�����⴩͸��!";
				propety.laserPropNum++;
			}else if(i==1){
				DrawGame.msg = "���һ�����п�Ͷ!";
				propety.airDropPropNum++;
			}else{
				DrawGame.msg = "���һ������!";
				propety.dartlePropNum++;
			}
			DrawGame.msgTime = System.currentTimeMillis()/1000;
		}else if(num >= 30 && !hasPropReward3){
			propety.limitBooldPropNum++;
			hasPropReward3=true;
			DrawGame.msg = "���һ������װ��!";
			DrawGame.msgTime = System.currentTimeMillis()/1000;
		}
	}
	
	/*�����Ϸ�еĶ���*/
	public void clearGamePlaying(){
		createRole.clearObject();
		weapon.clearObject();
		CreateRole.count=1; 
		createRole.clear();
		weapon.clear();
		draw.clearPlaying();
		boss=null;
		for(int i=0;i<exploders.length;i++){
			exploders[i] = null;
		}
	}
}
