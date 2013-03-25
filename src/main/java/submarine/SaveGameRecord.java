package submarine;

import cn.ohyeah.stb.game.ServiceWrapper;
import cn.ohyeah.stb.util.ConvertUtil;

public class SaveGameRecord implements Common{
	private SubmarineGameEngine engine;
	public SaveGameRecord(SubmarineGameEngine engine) {
		this.engine = engine;
	}

	/*存游戏成就,用于排名--返回0成功*/
	public int saveGameAttainment(Role own){
		ServiceWrapper sw = engine.getServiceWrapper();
		sw.saveScore(own.scores);
		//int medalNum = SubmarineGameEngine.medal + SubmarineGameEngine.medal2*4 + SubmarineGameEngine.medal3*16;
		return sw.getResult();
	}
	
	/*存游戏记录*/
	public int saveGameRecord(Role own, Role boss, int currLevel, int index){
		String datas = "";
		ServiceWrapper sw = engine.getServiceWrapper();
		datas = "currLevel:"+currLevel
				+",lifeNum:"+own.lifeNum
				+",limitLife:"+own.limitLife
				+",eatCount:"+own.eatCount
				+",eatCount2:"+own.eatCount2
				+",status:"+own.status
				+",nonceLife:"+own.nonceLife
				+",medal:"+SubmarineGameEngine.medal
				+",medal2:"+SubmarineGameEngine.medal2
				+",medal3:"+SubmarineGameEngine.medal3
				+",useHidePropNum:"+Propety.useHidePropNum
				+",useLimitBooldPropNum:"+Propety.useLimitBooldPropNum
				+",useMedigelPropNum:"+Propety.useMedigelPropNum
				+",dartleFlag:"+SubmarineGameEngine.dartleFlag
				+",id:"+own.id
				+",g_status:"+SubmarineGameEngine.g_status
				+",difficultLevel:"+SubmarineGameEngine.difficultLevel
				+",isOpenDifficult2:"+SubmarineGameEngine.isOpenDifficult2
				+",isOpenDifficult3:"+SubmarineGameEngine.isOpenDifficult3
				+",scores2:"+own.scores2
				+",hasPropReward:"+SubmarineGameEngine.hasPropReward
				+",hasPropReward2:"+SubmarineGameEngine.hasPropReward2
				+",hasPropReward3:"+SubmarineGameEngine.hasPropReward3
				+",scores:"+own.scores;
		
		if(SubmarineGameEngine.g_status==GAME_SUB_STATUS_PLAYING_BOSS){
			datas += ",nonceLife:"+boss.nonceLife
					+",mapx:"+boss.mapx
					+",mapy:"+boss.mapy;
		}else {
			datas += ",eatBlack:"+own.eatBlack
				     +",eatBlue:"+own.eatBlue
				     +",eatJisu:"+own.eatJisu
				     +",eatRed:"+own.eatRed
				     +",eatYellow:"+own.eatYellow
				     +",harm:"+own.harm;
		}	
		
		sw.saveRecord(index, datas);
		print(own);
		return sw.getResult();
	}
	
	
	/*读档--返回0成功*/
	public int loadGameRecord(int index){
		ServiceWrapper sw = engine.getServiceWrapper();
		String data = sw.loadRecord(index);
		if(!sw.isServiceSuccessful() || data==null || data.equals("0")){
			return -1;
		}
		String[] datas = ConvertUtil.split(data, ",");
		System.out.println("datas:"+datas);
		System.out.println(datas[0]);
		String[] d2 = new String[datas.length]; 
		for(int i=0;i<datas.length;i++){
			d2[i] = ConvertUtil.split(datas[i], ":")[1];
		}
		SubmarineGameEngine.currLevel = Integer.parseInt(d2[0]);
		SubmarineGameEngine.lifeNum = Integer.parseInt(d2[1]);
		SubmarineGameEngine.limitLife = Integer.parseInt(d2[2]);
		SubmarineGameEngine.eatCount = Integer.parseInt(d2[3]);
		SubmarineGameEngine.eatCount2 = Integer.parseInt(d2[4]);
		SubmarineGameEngine.submarineStatus = Integer.parseInt(d2[5]);
		SubmarineGameEngine.nonceLife = Integer.parseInt(d2[6]);
		SubmarineGameEngine.medal = Integer.parseInt(d2[7]);
		SubmarineGameEngine.medal2 = Integer.parseInt(d2[8]);
		SubmarineGameEngine.medal3 = Integer.parseInt(d2[9]);
		Propety.useHidePropNum = Integer.parseInt(d2[10]);
		Propety.useLimitBooldPropNum = Integer.parseInt(d2[11]);
		Propety.useMedigelPropNum = Integer.parseInt(d2[12]);
		SubmarineGameEngine.dartleFlag = d2[13].equals("true")?true:false;
		SubmarineGameEngine.submarineId = Integer.parseInt(d2[14]);
		SubmarineGameEngine.gameStatus = Integer.parseInt(d2[15]);
		SubmarineGameEngine.difficultLevel = Integer.parseInt(d2[16]);
		SubmarineGameEngine.isOpenDifficult2 = d2[17].equals("true")?true:false;
		SubmarineGameEngine.isOpenDifficult3 = d2[18].equals("true")?true:false;
		SubmarineGameEngine.scores2 = Integer.parseInt(d2[19]);
		SubmarineGameEngine.hasPropReward = d2[20].equals("true")?true:false;
		SubmarineGameEngine.hasPropReward2 = d2[21].equals("true")?true:false;
		SubmarineGameEngine.hasPropReward3 = d2[22].equals("true")?true:false;
		SubmarineGameEngine.scores = Integer.parseInt(d2[23]);
		
		if(SubmarineGameEngine.gameStatus==GAME_SUB_STATUS_PLAYING_BOSS){
			SubmarineGameEngine.bossLife = Integer.parseInt(d2[24]);
			SubmarineGameEngine.bossMapx = Integer.parseInt(d2[25]);
			SubmarineGameEngine.bossMapy = Integer.parseInt(d2[26]);
		}else{
			SubmarineGameEngine.eatBlack= Integer.parseInt(d2[24]);
			SubmarineGameEngine.eatBlue = Integer.parseInt(d2[25]);
			SubmarineGameEngine.eatJisu = Integer.parseInt(d2[26]);
			SubmarineGameEngine.eatRed = Integer.parseInt(d2[27]);
			SubmarineGameEngine.eatYellow = Integer.parseInt(d2[28]);
			SubmarineGameEngine.harm = Integer.parseInt(d2[29]);
		}
		
		System.out.println("currLevel: " + SubmarineGameEngine.currLevel);
		System.out.println("lifeNum: " + SubmarineGameEngine.lifeNum);
		System.out.println("limitLife: " + SubmarineGameEngine.limitLife);
		System.out.println("eatBlack: " + SubmarineGameEngine.eatBlack);
		System.out.println("eatBlue: " + SubmarineGameEngine.eatBlue);
		System.out.println("eatJisu: " + SubmarineGameEngine.eatJisu);
		System.out.println("eatRed: " + SubmarineGameEngine.eatRed);
		System.out.println("eatYellow: " + SubmarineGameEngine.eatYellow);
		System.out.println("harm: " + SubmarineGameEngine.harm);
		System.out.println("eatCount: " + SubmarineGameEngine.eatCount);
		System.out.println("eatCount2: " + SubmarineGameEngine.eatCount2);
		System.out.println("submarineStatus: " + SubmarineGameEngine.submarineStatus);
		System.out.println("nonceLife: " + SubmarineGameEngine.nonceLife);
		System.out.println("recordFlag: " + SubmarineGameEngine.recordFlag);
		//System.out.println("id:" + SubmarineGameEngine.id);
		System.out.println("gameStatus: " + SubmarineGameEngine.gameStatus);
		System.out.println("SubmarineGameEngine.hasPropReward: " + SubmarineGameEngine.hasPropReward);
		System.out.println("SubmarineGameEngine.hasPropReward2: " + SubmarineGameEngine.hasPropReward2);
		System.out.println("SubmarineGameEngine.hasPropReward3: " + SubmarineGameEngine.hasPropReward3);
	
		return sw.getResult();
	}
	
	private void print(Role own){
		System.out.println("own.id:" + own.id);
		System.out.println("own.lifeNum:" + own.lifeNum);
		System.out.println("own.limitLife:" + own.limitLife);
		System.out.println("own.eatBlack:" + own.eatBlack);
		System.out.println("own.eatBlue:" + own.eatBlue);
		System.out.println("own.eatJisu:" + own.eatJisu);
		System.out.println("own.eatRed:" + own.eatRed);
		System.out.println("own.eatYellow:" + own.eatYellow);
		System.out.println("harm: " + SubmarineGameEngine.harm);
		System.out.println("own.eatCount:" + own.eatCount);
		System.out.println("own.eatCount2:" + own.eatCount2);
		System.out.println("own.status:" + own.status);
		System.out.println("nonceLife: " + own.nonceLife);
		//System.out.println("id:" + own.id);
		System.out.println("g_status: " + SubmarineGameEngine.g_status);
		System.out.println("SubmarineGameEngine.hasPropReward: " + SubmarineGameEngine.hasPropReward);
		System.out.println("SubmarineGameEngine.hasPropReward2: " + SubmarineGameEngine.hasPropReward2);
		System.out.println("SubmarineGameEngine.hasPropReward3: " + SubmarineGameEngine.hasPropReward3);
	}
	
}
