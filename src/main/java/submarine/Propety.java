package submarine;

import cn.ohyeah.itvgame.model.OwnProp;
import cn.ohyeah.stb.game.EngineService;
import cn.ohyeah.stb.game.ServiceWrapper;
import cn.ohyeah.stb.game.StateRecharge;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.PopupConfirm;
import cn.ohyeah.stb.ui.PopupText;

public class Propety implements Common{
	
	protected  int slowPropNum;    	//减速道具数量
	protected  int laserPropNum;	//激光道具数量
	protected  int medigelPropNum;	//回血道具数量
	protected  int airDropPropNum;	//空投道具数量
	protected  int limitBooldPropNum;//加血上限道具数量		
	protected  int energyPropNum;	//能量防护道具数量
	protected  int hidePropNum;		//隐身道具数量
	protected  int dartlePropNum;	//连射道具数量	
	
	public static int useHidePropNum;	//使用隐身道具数量
	public static int useLimitBooldPropNum;//使用加生命道具数量
	public static int useMedigelPropNum;   //使用回血道具数量
	
	public static int[] propIds = {34,35,36,37,38,39,40,41};
	public static int[] propPrice = {30,40,50,30,60,20,30,40};
	
	private SubmarineGameEngine engine;
	
	public Propety(SubmarineGameEngine engine) {
		this.engine = engine;
	}
	
	/*查询用户所有的道具(要购买的潜艇除外)*/
 	public void queryOwnAllProps(){
		//OwnProp[] props = new OwnProp[8];
		ServiceWrapper sw = engine.getServiceWrapper();
		OwnProp[] props = sw.queryOwnPropList();
		if(props==null){
			return;
		}
		/*for(int i=0;i<props.length;i++){
			//props[ownProps[i].getPropId()-34] =  
			
			if(queryOwnProp(engineService, i+34)==null){
				OwnProp ownProp = new OwnProp();
				ownProp.setCount(0);
				ownProp.setPropId(i+34);
				props[i] = ownProp;
			}else{
				props[i] = queryOwnProp(engineService, i+34);
			}
		}*/
		for(int i=0;i<props.length;i++){
			if(props[i].getPropId()==37){
				airDropPropNum = props[i].getCount();
			}else if(props[i].getPropId()==41){
				dartlePropNum = props[i].getCount();
			}else if(props[i].getPropId()==34){
				slowPropNum = props[i].getCount();
			}else if(props[i].getPropId()==40){
				hidePropNum = props[i].getCount();
			}else if(props[i].getPropId()==35){
				laserPropNum = props[i].getCount();
			}else if(props[i].getPropId()==39){
				energyPropNum = props[i].getCount();
			}else if(props[i].getPropId()==38){
				limitBooldPropNum = props[i].getCount();
			}else if(props[i].getPropId()==36){
				medigelPropNum = props[i].getCount();
			}
			else if (props[i].getPropId()==42) {
				SubmarineGameEngine.isPurchase = true;
			}
			else if (props[i].getPropId()==43) {
				SubmarineGameEngine.isPurchase2 = true;
			}
		}
		System.out.println("airDropPropNum:"+airDropPropNum);
		System.out.println("dartlePropNum:"+dartlePropNum);
		System.out.println("slowPropNum:"+slowPropNum);
		System.out.println("hidePropNum:"+hidePropNum);
		System.out.println("laserPropNum:"+laserPropNum);
		System.out.println("energyPropNum:"+energyPropNum);
		System.out.println("limitBooldPropNum:"+limitBooldPropNum);
		System.out.println("medigelPropNum:"+medigelPropNum);
		System.out.println("isPurchase:"+SubmarineGameEngine.isPurchase);
		System.out.println("isPurchase2:"+SubmarineGameEngine.isPurchase2);
		//return props;
	}
	
	/*按道具ID查询*/
	//public OwnProp[] queryOwnProp(EngineService engineService){
	/*	ServiceWrapper sw = engine.getServiceWrapper();
		return sw.queryOwnPropList();*/
		/*
		OwnProp ownProp = null;
		if(sw.isServiceSuccessful() && props!=null){
			for(int i=0;i<props.length;i++){
				if(propId == props[i].getPropId()){
					ownProp = props[i];
					System.out.println("propId:"+ownProp.getPropId());
					System.out.println("propCount:"+ownProp.getCount());
				}
			}
		}*/
		//return ownProp;
	//}
	
	private boolean buyProp(int propId, int propCount, int price, String propName) {
		if (engine.getEngineService().getBalance() >= price) {
			ServiceWrapper sw = engine.getServiceWrapper();
			sw.purchaseProp(propId, propCount, "购买"+propName);
			PopupText pt = UIResource.getInstance().buildDefaultPopupText();
			if (sw.isServiceSuccessful()) {
				pt.setText("购买"+propName+"成功");
			}
			else {
				pt.setText("购买"+propName+"失败, 原因: "+sw.getServiceMessage());
				
			}
			pt.popup();
			return sw.isServiceSuccessful();
		}
		else {
			PopupConfirm pc = UIResource.getInstance().buildDefaultPopupConfirm();
			pc.setText("游戏币不足,是否充值");
			if (pc.popup() == 0) {
				StateRecharge recharge = new StateRecharge(engine);
				recharge.recharge();
			}
			return false;
		}
	}
	
	/*购买道具*/
	public void purchaseProp(Role own, int shopX, int shopY,EngineService engineService){
		if(shopX==0 && shopY==0){
			int propId = 39;
			if (buyProp(propId, 1, propPrice[5], "能量防护罩")) {
				energyPropNum++;
			}
		}
		if(shopX==1 && shopY==0){
			int propId = 40;
			if (buyProp(propId, 1, propPrice[6], "隐身装甲")) {
				hidePropNum++;
			}
		}
		if(shopX==0 && shopY==1){
			int propId = 34;
			if (buyProp(propId, 1, propPrice[0], "凝固导弹")) {
				slowPropNum++;
			}
		}
		if(shopX==1 && shopY==1){
			int propId = 37;
			if (buyProp(propId, 1, propPrice[3], "呼叫空投")) {
				airDropPropNum++;
			}
		}
		if(shopX==0 && shopY==2){
			int propId = 41;
			if(buyProp(propId, 1, propPrice[7], "连射")) {
				dartlePropNum++;
			}
		}
		if(shopX==1 && shopY==2){
			int propId = 35;
			if (buyProp(propId, 1, propPrice[1], "穿透激光弹")) {
				laserPropNum++;
			}
		}
		if(shopX==0 && shopY==3){
			int propId = 36;
			if (buyProp(propId, 1, propPrice[2], "维修机器人")) {
				medigelPropNum++;
			}
		}
		if(shopX==1 && shopY==3){
			int propId = 38;
			if (buyProp(propId, 1, propPrice[4], "再生装甲")) {
				limitBooldPropNum++;
			}
		}
		if(shopX==101){
			int propId = 42;
			boolean result = buyProp(propId, 1, 100, "漩涡号");
			if(result){
				SubmarineGameEngine.isPurchase = true;
			}
		}
		if(shopX==102){
			int propId = 43;
			boolean result = buyProp(propId, 1, 100, "桑尼号");
			if(result){
				SubmarineGameEngine.isPurchase2 = true;
			}
		}
	}
}
