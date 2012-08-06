package submarine;

/**
 * 角色属性 
 * @author xiaochen
 *
 */
public class Role{
	
	int id; 			//ID  (用户舰艇ID:100-; 地方舰艇ID:1-10; 武器ID:11-30; Boss ID:31-40)
	int mapx; 			//在地图上的横坐标
	int mapy; 			//在地图上的纵坐标
	int width;			//自身宽度
	int height;			//自身高度
	int frame; 			//帧
	int direction; 		//移动方向
	int status;  		//状态(0活动状态, 1死状态)
	int nonceLife;		//生命值
	int limitLife;		//生命值上线
	int money; 			//金币数
	int lifeNum; 		//生命数
	float speed;		//移动速度
	int scores; 		//积分
	int scores2;		//用于计算功勋的积分
	int skill; 			//技能
	int eatYellow;		//消灭黄潜艇数
	int eatRed;			//消灭红潜艇数
	int eatBlue;		//消灭蓝潜艇数
	int eatBlack;		//消灭核潜艇数
	int eatJisu;		//消灭急速潜艇数
	int attack; 		//NPC普通攻击力
	int eatCount;		//消灭潜艇总数量
	int eatCount2;		//出现BOSS所要击沉的数量
	int harm;			//所受的伤害
	
	
	Role role;   			//雷达区域中的npc
	
}
