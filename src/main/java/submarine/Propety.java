package submarine;

import cn.ohyeah.itvgame.model.OwnProp;
import cn.ohyeah.stb.game.EngineService;
import cn.ohyeah.stb.game.Recharge;
import cn.ohyeah.stb.game.ServiceWrapper;
import cn.ohyeah.stb.res.UIResource;
import cn.ohyeah.stb.ui.PopupConfirm;
import cn.ohyeah.stb.ui.PopupText;
import cn.ohyeah.stb.util.ConvertUtil;

public class Propety implements Common{
	
	protected  int slowPropNum;    	//���ٵ�������
	protected  int laserPropNum;	//�����������
	protected  int medigelPropNum;	//��Ѫ��������
	protected  int airDropPropNum;	//��Ͷ��������
	protected  int limitBooldPropNum;//��Ѫ���޵�������		
	protected  int energyPropNum;	//����������������
	protected  int hidePropNum;		//�����������
	protected  int dartlePropNum;	//�����������	
	
	public static int useHidePropNum;	//ʹ�������������
	public static int useLimitBooldPropNum;//ʹ�ü�������������
	public static int useMedigelPropNum;   //ʹ�û�Ѫ��������
	
	public static int[] propIds = {34,35,36,37,38,39,40,41,42,43};
	public static int[] propPrice = {30,40,50,30,60,20,30,40,100,100};
	
	private SubmarineGameEngine engine;
	
	public Propety(SubmarineGameEngine engine) {
		this.engine = engine;
	}
	
	/*�������*/
	public void saveProp(Propety p){
		ServiceWrapper sw = engine.getServiceWrapper();
		String datas = "id:"+propIds[0]+",price:"+propPrice[0]+",num:"+p.slowPropNum
					   +"|id:"+	+propIds[1]+",price:"+propPrice[1]+",num:"+p.laserPropNum
					   +"|id:"+	+propIds[2]+",price:"+propPrice[2]+",num:"+p.medigelPropNum
					   +"|id:"+	+propIds[3]+",price:"+propPrice[3]+",num:"+p.airDropPropNum
					   +"|id:"+	+propIds[4]+",price:"+propPrice[4]+",num:"+p.limitBooldPropNum
					   +"|id:"+	+propIds[5]+",price:"+propPrice[5]+",num:"+p.energyPropNum
					   +"|id:"+	+propIds[6]+",price:"+propPrice[6]+",num:"+p.hidePropNum
					   +"|id:"+	+propIds[7]+",price:"+propPrice[7]+",num:"+p.dartlePropNum
					   +"|id:"+	+propIds[8]+",price:"+propPrice[8]+",num:"+(engine.isPurchase==true?1:0)
					   +"|id:"+	+propIds[9]+",price:"+propPrice[9]+",num:"+(engine.isPurchase2==true?1:0);
		System.out.println("save prop datas:"+datas);
		sw.savePropItem(datas);
	}
	
	/*��ѯ�û����еĵ���(Ҫ�����Ǳͧ����)*/
 	public void queryOwnAllProps(){
		ServiceWrapper sw = engine.getServiceWrapper();
		String datas = sw.loadPropItem();
		if(datas == null || datas == "" || datas.equals("0")){
			saveProp(this);
			return;
		}
		OwnProp[] props = new OwnProp[10];
		String[] data1 = ConvertUtil.split(datas, "|");
		for(int j=0;j<data1.length;j++){
			OwnProp prop = new OwnProp();
			String[] data2 = ConvertUtil.split(data1[j], ",");
			String[] data3 = new String[data2.length];
			for(int k=0;k<data2.length;k++){
				data3[k] = ConvertUtil.split(data2[k], ":")[1];
			}
			prop.setPropId(Integer.parseInt(data3[0]));
			prop.setCount(Integer.parseInt(data3[2]));
			props[j] = prop;
		}
		
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
				if(props[i].getCount()>0){
					SubmarineGameEngine.isPurchase = true;
				}else{
					SubmarineGameEngine.isPurchase = false;
				}
			}
			else if (props[i].getPropId()==43) {
				if(props[i].getCount()>0){
					SubmarineGameEngine.isPurchase2 = true;
				}else{
					SubmarineGameEngine.isPurchase2 = false;
				}
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
	
	/*������ID��ѯ*/
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
			sw.consume(1, price);
			PopupText pt = UIResource.getInstance().buildDefaultPopupText();
			if (sw.isServiceSuccessful()) {
				pt.setText("����"+propName+"�ɹ�");
			}
			else {
				pt.setText("����"+propName+"ʧ��, ԭ��: "+sw.getMessage());
				
			}
			pt.popup();
			return sw.isServiceSuccessful();
		}
		else {
			PopupConfirm pc = UIResource.getInstance().buildDefaultPopupConfirm();
			pc.setText("��Ϸ�Ҳ���,�Ƿ��ֵ");
			if (pc.popup() == 0) {
				Recharge recharge = new Recharge(engine);
				recharge.recharge();
			}
			return false;
		}
	}
	
	/*�������*/
	public void purchaseProp(Role own, int shopX, int shopY,EngineService engineService){
		if(shopX==0 && shopY==0){
			int propId = 39;
			if (buyProp(propId, 1, propPrice[5], "����������")) {
				energyPropNum++;
			}
		}
		if(shopX==1 && shopY==0){
			int propId = 40;
			if (buyProp(propId, 1, propPrice[6], "����װ��")) {
				hidePropNum++;
			}
		}
		if(shopX==0 && shopY==1){
			int propId = 34;
			if (buyProp(propId, 1, propPrice[0], "���̵���")) {
				slowPropNum++;
			}
		}
		if(shopX==1 && shopY==1){
			int propId = 37;
			if (buyProp(propId, 1, propPrice[3], "���п�Ͷ")) {
				airDropPropNum++;
			}
		}
		if(shopX==0 && shopY==2){
			int propId = 41;
			if(buyProp(propId, 1, propPrice[7], "����")) {
				dartlePropNum++;
			}
		}
		if(shopX==1 && shopY==2){
			int propId = 35;
			if (buyProp(propId, 1, propPrice[1], "��͸���ⵯ")) {
				laserPropNum++;
			}
		}
		if(shopX==0 && shopY==3){
			int propId = 36;
			if (buyProp(propId, 1, propPrice[2], "ά�޻�����")) {
				medigelPropNum++;
			}
		}
		if(shopX==1 && shopY==3){
			int propId = 38;
			if (buyProp(propId, 1, propPrice[4], "����װ��")) {
				limitBooldPropNum++;
			}
		}
		if(shopX==101){
			int propId = 42;
			boolean result = buyProp(propId, 1, 100, "���к�");
			if(result){
				SubmarineGameEngine.isPurchase = true;
			}
		}
		if(shopX==102){
			int propId = 43;
			boolean result = buyProp(propId, 1, 100, "ɣ���");
			if(result){
				SubmarineGameEngine.isPurchase2 = true;
			}
		}
	}
}
