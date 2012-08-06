package submarine;


/**
 * 技能攻击
 * 
 * @author xiaochen
 * 
 */
public class Attacks implements Common {
	
	/* 普通子弹攻击 */
	public void bombAttack(Role role, Role own) {
		if (weapon.bombs.size() > 0) {
			Weapon bomb = null;
			for (int i = 0; i < weapon.bombs.size(); i++) {
				bomb = (Weapon) weapon.bombs.elementAt(i);
				int bombH=bomb.mapy;
				if(own.id==100){
					bombH = bomb.mapy+bomb.height/2+10;
				}
				Role npc = null;
				if (bomb.direction == 2) { // 攻击敌方舰艇
					for (int j = 0; j < CreateRole.npcs.size(); j++) {
						npc = (Role) CreateRole.npcs.elementAt(j);
						if ((((bomb.mapx>=npc.mapx && bomb.mapx<=(npc.mapx+npc.width)) 
							|| ((bomb.mapx+bomb.width)>=npc.mapx && (bomb.mapx+bomb.width)<=(npc.mapx+npc.width)))
							&& ((npc.mapy<=(bomb.mapy+bomb.height)) && ((npc.mapy+npc.height) >= bombH)))) {
							
							hitNpc(own,npc,bomb);
							/*SubmarineGameEngine.bombFlag = true;
							SubmarineGameEngine.bombTime1=System.currentTimeMillis()/100;*/
							SubmarineGameEngine.bombX = bomb.mapx+bomb.width/2-SubmarineGameEngine.bombImgW;
							SubmarineGameEngine.bombY = bomb.mapy+bomb.height-SubmarineGameEngine.bombImgH;
							Exploder exploder = new Exploder(SubmarineGameEngine.bombX,SubmarineGameEngine.bombY);
							SubmarineGameEngine.exploders[SubmarineGameEngine.eIndex] = exploder;
							if(SubmarineGameEngine.eIndex < SubmarineGameEngine.exploders.length-1){
								SubmarineGameEngine.eIndex ++;
							}else{
								SubmarineGameEngine.eIndex=0;
							}
							Weapon.hitNumber++;
							weapon.bombs.removeElement(bomb);
						}
					}
					if(role != null){ //boss
						if ((((bomb.mapx>=role.mapx && bomb.mapx<=(role.mapx+role.width)) 
								|| ((bomb.mapx+bomb.width)>=role.mapx && (bomb.mapx+bomb.width)<=(role.mapx+role.width)))
								&& ((role.mapy<=(bomb.mapy+bomb.height)) && ((role.mapy+role.height) >= bombH)))){
							//own.scores+=role.scores;
							if(role.status!=2){
								/*SubmarineGameEngine.bombFlag = true;
								SubmarineGameEngine.bombTime1=System.currentTimeMillis()/100;*/
								SubmarineGameEngine.bombX = bomb.mapx+bomb.width/2-SubmarineGameEngine.bombImgW;
								SubmarineGameEngine.bombY = bomb.mapy+bomb.height-SubmarineGameEngine.bombImgH;
								Exploder exploder = new Exploder(SubmarineGameEngine.bombX,SubmarineGameEngine.bombY);
								SubmarineGameEngine.exploders[SubmarineGameEngine.eIndex] = exploder;
								if(SubmarineGameEngine.eIndex < SubmarineGameEngine.exploders.length-1){
									SubmarineGameEngine.eIndex ++;
								}else{
									SubmarineGameEngine.eIndex=0;
								}
								Weapon.hitNumber++;
								weapon.bombs.removeElement(bomb);
								SubmarineGameEngine.gameBufferStartTime = System.currentTimeMillis()/1000;
								role.nonceLife -= bomb.harm;
								weapon.bombs.removeElement(bomb);
							}
						}
					}
				} else { // 被敌方攻击
				/*	if ((((bomb.mapx>=own.mapx && bomb.mapx<=(own.mapx+own.width)) 
							|| ((bomb.mapx+bomb.width)>=own.mapx && (bomb.mapx+bomb.width)<=(own.mapx+own.width)))
							&& (bomb.mapy<=(own.mapy+own.height) && bomb.mapy>=own.mapy))) {*/
					if((((own.mapx+own.width)>=bomb.mapx && own.mapx<=bomb.mapx) 
							||((own.mapx<=(bomb.mapx+bomb.width)) && (own.mapx+own.width)>=(bomb.mapx+bomb.width)))
							&& (bomb.mapy>=own.mapy && bomb.mapy<=own.mapy+own.height)){
						if(own.status!=2){ //隐身状态
							if(!SubmarineGameEngine.protectionFlag){ //能量保护状态
								if(own.nonceLife>0){
									if(own.nonceLife>20){
										own.nonceLife -= 20;
										own.harm += 20;
									}else{
										own.nonceLife=0;
										own.harm += own.nonceLife;
									}
									SubmarineGameEngine.harm = own.harm;
									if(own.nonceLife>=20){
										SubmarineGameEngine.harmFlag = true;
										SubmarineGameEngine.harmTime = System.currentTimeMillis()/100;
										own.status=2;
									}
									//每减20点血减一个勋章
									if(SubmarineGameEngine.medal>0){
										SubmarineGameEngine.medal--;
									}else if(SubmarineGameEngine.medal2>0){
										SubmarineGameEngine.medal2--;
										SubmarineGameEngine.medal=3;
									}else if(SubmarineGameEngine.medal3>0){
										SubmarineGameEngine.medal3--;
										SubmarineGameEngine.medal=3;
										SubmarineGameEngine.medal2=3;
									}
								}
								if(own.nonceLife <= 0){
									own.status=1;
									if(own.lifeNum>0){
										own.lifeNum--;
									}
									Weapon.isNet=false; //去掉网住效果
									SubmarineGameEngine.isMove=true;//恢复移动
									SubmarineGameEngine.lifeNum = own.lifeNum;
									SubmarineGameEngine.revivalTime1 = System.currentTimeMillis()/1000;
									SubmarineGameEngine.gameBufferStartTime = System.currentTimeMillis()/1000;

								}
								/*SubmarineGameEngine.bombFlag = true;
								SubmarineGameEngine.bombTime1=System.currentTimeMillis()/100;*/
								SubmarineGameEngine.bombX = bomb.mapx+bomb.width/2-SubmarineGameEngine.bombImgW;
								SubmarineGameEngine.bombY = bomb.mapy-SubmarineGameEngine.bombImgH;
								Exploder exploder = new Exploder(SubmarineGameEngine.bombX,SubmarineGameEngine.bombY);
								SubmarineGameEngine.exploders[SubmarineGameEngine.eIndex] = exploder;
								if(SubmarineGameEngine.eIndex < SubmarineGameEngine.exploders.length-1){
									SubmarineGameEngine.eIndex ++;
								}else{
									SubmarineGameEngine.eIndex=0;
								}
							}
							weapon.bombs.removeElement(bomb);
						}
						
						if(SubmarineGameEngine.protectionFlag){
							SubmarineGameEngine.pFlag=0;
							SubmarineGameEngine.protectionFlag=false;
						}
					}
				}
			}
		}
	}
	
	/*呼叫空投攻击(技能)*/
	public void skillAttack(Role role,Role own){
		if (weapon.paraDrops.size() > 0) {
			Weapon w = null;
			for (int i = 0; i < weapon.paraDrops.size(); i++) {
				w = (Weapon) weapon.paraDrops.elementAt(i);
				int bombH=w.mapy;
				if(own.id==100){
					bombH = w.mapy+w.height/2+10;
				}
				Role npc = null;
				if (w.direction == 2) { // 攻击敌方舰艇
					for (int j = 0; j < CreateRole.npcs.size(); j++) {
						npc = (Role) CreateRole.npcs.elementAt(j);
						if ((((w.mapx>=npc.mapx && w.mapx<=(npc.mapx+npc.width)) 
								|| ((w.mapx+w.width)>=npc.mapx && (w.mapx+w.width)<=(npc.mapx+npc.width)))
								&& ((npc.mapy<=(w.mapy+w.height)) && ((npc.mapy+npc.height) >= bombH)))) {
							
							hitNpc(own,npc,w);
							/*SubmarineGameEngine.bombFlag = true;
							SubmarineGameEngine.bombTime1=System.currentTimeMillis()/100;*/
							SubmarineGameEngine.bombX = w.mapx+w.width/2-SubmarineGameEngine.bombImgW;
							SubmarineGameEngine.bombY = w.mapy+w.height-SubmarineGameEngine.bombImgH;
							Exploder exploder = new Exploder(SubmarineGameEngine.bombX,SubmarineGameEngine.bombY);
							SubmarineGameEngine.exploders[SubmarineGameEngine.eIndex] = exploder;
							if(SubmarineGameEngine.eIndex < SubmarineGameEngine.exploders.length-1){
								SubmarineGameEngine.eIndex ++;
							}else{
								SubmarineGameEngine.eIndex=0;
							}
							weapon.paraDrops.removeElement(w);
						}
					}
					if(role != null){ //boss
						if ((((w.mapx>=role.mapx && w.mapx<=(role.mapx+role.width)) 
								|| ((w.mapx+w.width)>=role.mapx && (w.mapx+w.width)<=(role.mapx+role.width)))
								&& ((role.mapy<=(w.mapy+w.height)) && ((role.mapy+role.height) >= bombH)))){
							
							//own.scores+=role.scores;
							role.nonceLife -= w.harm;
							SubmarineGameEngine.gameBufferStartTime = System.currentTimeMillis()/1000;
							/*SubmarineGameEngine.bombFlag = true;
							SubmarineGameEngine.bombTime1=System.currentTimeMillis()/100;*/
							SubmarineGameEngine.bombX = w.mapx+w.width/2-SubmarineGameEngine.bombImgW;
							SubmarineGameEngine.bombY = w.mapy+w.height-SubmarineGameEngine.bombImgH;
							Exploder exploder = new Exploder(SubmarineGameEngine.bombX,SubmarineGameEngine.bombY);
							SubmarineGameEngine.exploders[SubmarineGameEngine.eIndex] = exploder;
							if(SubmarineGameEngine.eIndex < SubmarineGameEngine.exploders.length-1){
								SubmarineGameEngine.eIndex ++;
							}else{
								SubmarineGameEngine.eIndex=0;
							}
							weapon.paraDrops.removeElement(w);
						}
					}
					/*攻击水雷*/
					Weapon torpedo = null;
					for(int k=0;k<weapon.torpedos.size();k++){
						torpedo = (Weapon) weapon.torpedos.elementAt(k);
						if ((((w.mapx>=torpedo.mapx && w.mapx<=(torpedo.mapx+torpedo.width)) 
								|| ((w.mapx+w.width)>=torpedo.mapx && (w.mapx+w.width)<=(torpedo.mapx+torpedo.width)))
								&& ((torpedo.mapy<=(w.mapy+w.height)) && ((torpedo.mapy+torpedo.height) >= bombH)))){
							
							/*SubmarineGameEngine.bombFlag = true;
							SubmarineGameEngine.bombTime1=System.currentTimeMillis()/100;*/
							SubmarineGameEngine.bombX = w.mapx+w.width/2-SubmarineGameEngine.bombImgW;
							SubmarineGameEngine.bombY = w.mapy+w.height-SubmarineGameEngine.bombImgH;
							Exploder exploder = new Exploder(SubmarineGameEngine.bombX,SubmarineGameEngine.bombY);
							SubmarineGameEngine.exploders[SubmarineGameEngine.eIndex] = exploder;
							if(SubmarineGameEngine.eIndex < SubmarineGameEngine.exploders.length-1){
								SubmarineGameEngine.eIndex ++;
							}else{
								SubmarineGameEngine.eIndex=0;
							}
							weapon.torpedos.removeElement(torpedo);
							weapon.paraDrops.removeElement(w);
						}
					}
				}
			}
		}
	}
	
	/*连射攻击(技能)*/
	public void skill2Attack(Role role, Role own){
		if (weapon.dartles.size() > 0) {
			Weapon w = null;
			for (int i = 0; i < weapon.dartles.size(); i++) {
				w = (Weapon) weapon.dartles.elementAt(i);
				int bombH=w.mapy;
				if(own.id==100){
					bombH = w.mapy+w.height/2+10;
				}
				Role npc = null;
				if (w.direction == 2) { // 攻击敌方舰艇
					for (int j = 0; j < CreateRole.npcs.size(); j++) {
						npc = (Role) CreateRole.npcs.elementAt(j);
						if ((((w.mapx>=npc.mapx && w.mapx<=(npc.mapx+npc.width)) 
								|| ((w.mapx+w.width)>=npc.mapx && (w.mapx+w.width)<=(npc.mapx+npc.width)))
								&& ((npc.mapy<=(w.mapy+w.height)) && ((npc.mapy+npc.height) >= bombH)))) {
							
							hitNpc(own,npc,w);
							/*SubmarineGameEngine.bombFlag = true;
							SubmarineGameEngine.bombTime1=System.currentTimeMillis()/100;*/
							SubmarineGameEngine.bombX = w.mapx+w.width/2-SubmarineGameEngine.bombImgW;
							SubmarineGameEngine.bombY = w.mapy+w.height-SubmarineGameEngine.bombImgH;
							Exploder exploder = new Exploder(SubmarineGameEngine.bombX,SubmarineGameEngine.bombY);
							SubmarineGameEngine.exploders[SubmarineGameEngine.eIndex] = exploder;
							if(SubmarineGameEngine.eIndex < 100){
								SubmarineGameEngine.eIndex ++;
							}else{
								SubmarineGameEngine.eIndex=0;
							}
							Weapon.hitNumber++;
							weapon.bombs.removeElement(w);
							CreateRole.npcs.removeElement(npc);
							CreateRole.count--;
						}
					}
					if(role != null){ //boss
						if ((((w.mapx>=role.mapx && w.mapx<=(role.mapx+role.width)) 
								|| ((w.mapx+w.width)>=role.mapx && (w.mapx+w.width)<=(role.mapx+role.width)))
								&& ((role.mapy<=(w.mapy+w.height)) && ((role.mapy+role.height) >= bombH)))){
							
							if(role.status!=2){
								SubmarineGameEngine.gameBufferStartTime = System.currentTimeMillis()/1000;
								role.nonceLife -= w.harm;
								//own.scores+=role.scores;
								/*SubmarineGameEngine.bombFlag = true;
								SubmarineGameEngine.bombTime1=System.currentTimeMillis()/100;*/
								SubmarineGameEngine.bombX = w.mapx+w.width/2-SubmarineGameEngine.bombImgW;
								SubmarineGameEngine.bombY = w.mapy+w.height-SubmarineGameEngine.bombImgH;
								Exploder exploder = new Exploder(SubmarineGameEngine.bombX,SubmarineGameEngine.bombY);
								SubmarineGameEngine.exploders[SubmarineGameEngine.eIndex] = exploder;
								if(SubmarineGameEngine.eIndex < SubmarineGameEngine.exploders.length-1){
									SubmarineGameEngine.eIndex ++;
								}else{
									SubmarineGameEngine.eIndex=0;
								}
								Weapon.hitNumber++;
								weapon.bombs.removeElement(w);
							}
						}
					}
				} 
			}
		}
	}
	
	/*穿透激光弹--技能*/
	public void skill3Attack(Role role, Role own){
		if (weapon.lasers.size() > 0) {
			Weapon w = null;
			for (int i = 0; i < weapon.lasers.size(); i++) {
				w = (Weapon) weapon.lasers.elementAt(i);
				Role npc = null;
				if (w.direction == 2) { // 攻击敌方舰艇
					for (int j = 0; j < CreateRole.npcs.size(); j++) {
						npc = (Role) CreateRole.npcs.elementAt(j);
						if (((npc.mapx+npc.width)>=(w.mapx+40) && npc.mapx<=(w.mapx+40)) 
								||((npc.mapx<=(w.mapx+w.width-40)) && (npc.mapx+npc.width)>=(w.mapx+w.width-40))) {
							
							if(npc.id==1){
								own.eatYellow++;
								own.eatCount++;
								own.eatCount2++;
								own.scores += npc.scores;
								own.scores2 += npc.scores;
								Weapon.hitNumber++;
								SubmarineGameEngine.eatYellow=own.eatYellow;
							}else if(npc.id==2){
								own.eatRed++;
								own.eatCount++;
								own.eatCount2++;
								own.scores += npc.scores;
								own.scores2 += npc.scores;
								Weapon.hitNumber++;
								SubmarineGameEngine.eatRed=own.eatRed;
							}else if(npc.id==3){
								own.eatBlue++;
								own.eatCount++;
								own.eatCount2++;
								own.scores += npc.scores;
								own.scores2 += npc.scores;
								Weapon.hitNumber++;
								SubmarineGameEngine.eatBlue=own.eatBlue;
							}else if(npc.id==4){
								own.eatBlack++;
								own.eatCount++;
								own.eatCount2++;
								own.scores += npc.scores;
								own.scores2 += npc.scores;
								Weapon.hitNumber++;
								SubmarineGameEngine.eatBlack=own.eatBlack;
							}else if(npc.id==5){
								own.eatJisu++;
								own.eatCount++;
								own.eatCount2++;
								own.scores += npc.scores;
								own.scores2 += npc.scores;
								Weapon.hitNumber++;
								SubmarineGameEngine.eatJisu=own.eatJisu;
							}
							SubmarineGameEngine.eatCount=own.eatCount;
							SubmarineGameEngine.eatCount2=own.eatCount2;
							SubmarineGameEngine.scores=own.scores;
							SubmarineGameEngine.scores2=own.scores2;
							
							if(npc.direction==0){
								SubmarineGameEngine.bombX = npc.mapx-SubmarineGameEngine.bombImgW;
								SubmarineGameEngine.bombY = npc.mapy-SubmarineGameEngine.bombImgH;
							}else{
								SubmarineGameEngine.bombX = npc.mapx+npc.width-SubmarineGameEngine.bombImgW;
								SubmarineGameEngine.bombY = npc.mapy-SubmarineGameEngine.bombImgH;
							}
							Exploder exploder = new Exploder(SubmarineGameEngine.bombX,SubmarineGameEngine.bombY);
							SubmarineGameEngine.exploders[SubmarineGameEngine.eIndex] = exploder;
							if(SubmarineGameEngine.eIndex < SubmarineGameEngine.exploders.length-1){
								SubmarineGameEngine.eIndex ++;
							}else{
								SubmarineGameEngine.eIndex=0;
							}
							CreateRole.npcs.removeElement(npc);
							CreateRole.count--;
						}
					}
					if(role != null){ //boss
						if (((role.mapx+role.width)>=(w.mapx+40) && role.mapx<=(w.mapx+40)) 
								||((role.mapx<=(w.mapx+w.width-40)) && (role.mapx+role.width)>=(w.mapx+w.width-40))){
							
								//own.scores+=role.scores;
							SubmarineGameEngine.bombFlag = true;
							if(role.direction==0){
								SubmarineGameEngine.bombX = w.mapx+30;//role.mapx-SubmarineGameEngine.bombImgW;
								SubmarineGameEngine.bombY = role.mapy-20;//role.mapy-SubmarineGameEngine.bombImgH;
							}else{
								SubmarineGameEngine.bombX = w.mapx+30;//role.mapx-SubmarineGameEngine.bombImgW;
								SubmarineGameEngine.bombY = role.mapy-20;//role.mapy-SubmarineGameEngine.bombImgH;
							}
							SubmarineGameEngine.gameBufferStartTime = System.currentTimeMillis()/1000;
							role.nonceLife -= w.harm;
						}
					}
				}
			}
		}
	}
	
	/*敌方技能--网*/
	public void bossSkillAttack(Role role, Role own){
		for (int i = 0; i < weapon.nets.size(); i++) {
			Weapon w = (Weapon) weapon.nets.elementAt(i);
			if((((own.mapx+own.width)>=w.mapx && own.mapx<=w.mapx) 
					||((own.mapx<=(w.mapx+w.width)) && (own.mapx+own.width)>=(w.mapx+w.width)))
					&& (w.mapy>=own.mapy && w.mapy<=own.mapy+own.height)){
				
				Weapon.isNet=true;
				SubmarineGameEngine.netTime=System.currentTimeMillis()/1000;
				weapon.nets.removeElement(w);
			}
		}
	}
	/*敌方技能--水雷*/
	public void bossSkillAttack2(Role role, Role own){
		for (int i = 0; i < weapon.torpedos.size(); i++) {
			Weapon w = (Weapon) weapon.torpedos.elementAt(i);
			if((((own.mapx+own.width)>=w.mapx && own.mapx<=w.mapx) 
					||((own.mapx<=(w.mapx+w.width)) && (own.mapx+own.width)>=(w.mapx+w.width)))
					&& (w.mapy>=own.mapy && w.mapy<=own.mapy+own.height)){
				
				//if(own.status!=2){ //隐身状态
					if(!SubmarineGameEngine.protectionFlag){ //能量保护状态
						if(own.nonceLife>0){
							if(own.nonceLife>20){
								own.nonceLife -= 20;
								own.harm += 20;
							}else{
								own.nonceLife=0;
								own.harm += own.nonceLife;
							}
							SubmarineGameEngine.harm = own.harm;
							if(own.nonceLife>=20){
								SubmarineGameEngine.harmFlag = true;
								SubmarineGameEngine.harmTime = System.currentTimeMillis()/100;
								own.status=2;
							}
							//每减20点血减一个勋章
							if(SubmarineGameEngine.medal>0){
								SubmarineGameEngine.medal--;
							}else if(SubmarineGameEngine.medal2>0){
								SubmarineGameEngine.medal2--;
								SubmarineGameEngine.medal=3;
							}else if(SubmarineGameEngine.medal3>0){
								SubmarineGameEngine.medal3--;
								SubmarineGameEngine.medal=3;
								SubmarineGameEngine.medal2=3;
							}
						}
						if(own.nonceLife <= 0){
							own.status=1;
							if(own.lifeNum>0){
								own.lifeNum--;
							}
							Weapon.isNet=false; //去掉网住效果
							SubmarineGameEngine.isMove=true;//恢复移动
							SubmarineGameEngine.lifeNum = own.lifeNum;
							SubmarineGameEngine.revivalTime1 = System.currentTimeMillis()/1000;
							SubmarineGameEngine.gameBufferStartTime = System.currentTimeMillis()/1000;
						}
						/*SubmarineGameEngine.bombFlag = true;
						SubmarineGameEngine.bombTime1=System.currentTimeMillis()/100;*/
						SubmarineGameEngine.bombX = w.mapx+w.width/2-SubmarineGameEngine.bombImgW;
						SubmarineGameEngine.bombY = w.mapy-SubmarineGameEngine.bombImgH;
						Exploder exploder = new Exploder(SubmarineGameEngine.bombX,SubmarineGameEngine.bombY);
						SubmarineGameEngine.exploders[SubmarineGameEngine.eIndex] = exploder;
						if(SubmarineGameEngine.eIndex < SubmarineGameEngine.exploders.length-1){
							SubmarineGameEngine.eIndex ++;
						}else{
							SubmarineGameEngine.eIndex=0;
						}
					}
					weapon.torpedos.removeElement(w);
				//}
				
				if(SubmarineGameEngine.protectionFlag){
					SubmarineGameEngine.pFlag=0;
					SubmarineGameEngine.protectionFlag=false;
				}
			}
		}
	}
	/*敌方技能--空投*/
	public void bossSkillAttack3(Role role, Role own){
		for (int i = 0; i < weapon.airDrops.size(); i++) {
			Weapon w = (Weapon) weapon.airDrops.elementAt(i);
			if((((own.mapx+own.width)>=w.mapx && own.mapx<=w.mapx) 
					||((own.mapx<=(w.mapx+w.width)) && (own.mapx+own.width)>=(w.mapx+w.width)))
					&& (w.mapy>=own.mapy && w.mapy<=own.mapy+own.height)){
				
				//if(own.status!=2){ //隐身状态
					if(!SubmarineGameEngine.protectionFlag){ //能量保护状态
						if(own.nonceLife>0){
							if(own.nonceLife>20){
								own.nonceLife -= 20;
								own.harm += 20;
							}else{
								own.nonceLife=0;
								own.harm += own.nonceLife;
							}
							SubmarineGameEngine.harm = own.harm;
							if(own.nonceLife>=20){
								SubmarineGameEngine.harmFlag = true;
								SubmarineGameEngine.harmTime = System.currentTimeMillis()/100;
								own.status=2;
							}
							//每减20点血减一个勋章
							if(SubmarineGameEngine.medal>0){
								SubmarineGameEngine.medal--;
							}else if(SubmarineGameEngine.medal2>0){
								SubmarineGameEngine.medal2--;
								SubmarineGameEngine.medal=3;
							}else if(SubmarineGameEngine.medal3>0){
								SubmarineGameEngine.medal3--;
								SubmarineGameEngine.medal=3;
								SubmarineGameEngine.medal2=3;
							}
						}
						if(own.nonceLife <= 0){
							own.status=1;
							if(own.lifeNum>0){
								own.lifeNum--;
							}
							Weapon.isNet=false; //去掉网住效果
							SubmarineGameEngine.isMove=true;//恢复移动
							SubmarineGameEngine.lifeNum = own.lifeNum;
							SubmarineGameEngine.revivalTime1 = System.currentTimeMillis()/1000;
							SubmarineGameEngine.gameBufferStartTime = System.currentTimeMillis()/1000;
						}
						/*SubmarineGameEngine.bombFlag = true;
						SubmarineGameEngine.bombTime1=System.currentTimeMillis()/100;*/
						SubmarineGameEngine.bombX = w.mapx+w.width/2-SubmarineGameEngine.bombImgW;
						SubmarineGameEngine.bombY = w.mapy-SubmarineGameEngine.bombImgH;
						Exploder exploder = new Exploder(SubmarineGameEngine.bombX,SubmarineGameEngine.bombY);
						SubmarineGameEngine.exploders[SubmarineGameEngine.eIndex] = exploder;
						if(SubmarineGameEngine.eIndex < SubmarineGameEngine.exploders.length-1){
							SubmarineGameEngine.eIndex ++;
						}else{
							SubmarineGameEngine.eIndex=0;
						}
					}
					weapon.airDrops.removeElement(w);
				//}
				
				if(SubmarineGameEngine.protectionFlag){
					SubmarineGameEngine.pFlag=0;
					SubmarineGameEngine.protectionFlag=false;
				}
			}
		}
	}
	
	private void hitNpc(Role own,Role npc, Weapon w){
		if(npc.id==1){
			own.eatYellow++;
			own.eatCount++;
			own.eatCount2++;
			own.scores += npc.scores;
			own.scores2 += npc.scores;
			SubmarineGameEngine.eatCount=own.eatCount;
			SubmarineGameEngine.eatCount2=own.eatCount2;
			SubmarineGameEngine.scores=own.scores;
			SubmarineGameEngine.scores2=own.scores2;
			SubmarineGameEngine.eatYellow=own.eatYellow;
			CreateRole.npcs.removeElement(npc);
			CreateRole.count--;
		}else if(npc.id==2){
			npc.nonceLife -= w.harm;
			if(npc.nonceLife<=0){
				own.eatRed++;
				SubmarineGameEngine.eatRed=own.eatRed;
				own.eatCount++;
				own.eatCount2++;
				own.scores += npc.scores;
				own.scores2 += npc.scores;
				SubmarineGameEngine.eatCount=own.eatCount;
				SubmarineGameEngine.eatCount2=own.eatCount2;
				SubmarineGameEngine.scores=own.scores;
				SubmarineGameEngine.scores2=own.scores2;
				CreateRole.npcs.removeElement(npc);
				CreateRole.count--;
			}
		}else if(npc.id==3){
			npc.nonceLife -= w.harm;
			if(npc.nonceLife<=0){
				own.eatBlue++;
				SubmarineGameEngine.eatBlue=own.eatBlue;
				own.eatCount++;
				own.eatCount2++;
				own.scores += npc.scores;
				own.scores2 += npc.scores;
				SubmarineGameEngine.eatCount=own.eatCount;
				SubmarineGameEngine.eatCount2=own.eatCount2;
				SubmarineGameEngine.scores=own.scores;
				SubmarineGameEngine.scores2=own.scores2;
				CreateRole.npcs.removeElement(npc);
				CreateRole.count--;
			}
		}else if(npc.id==4){
			npc.nonceLife -= w.harm;
			if(npc.nonceLife<=0){
				own.eatBlack++;
				SubmarineGameEngine.eatBlack=own.eatBlack;
				own.eatCount++;
				own.eatCount2++;
				own.scores += npc.scores;
				own.scores2 += npc.scores;
				SubmarineGameEngine.eatCount=own.eatCount;
				SubmarineGameEngine.eatCount2=own.eatCount2;
				SubmarineGameEngine.scores=own.scores;
				SubmarineGameEngine.scores2=own.scores2;
				CreateRole.npcs.removeElement(npc);
				CreateRole.count--;
			}
		}else if(npc.id==5){
			npc.nonceLife -= w.harm;
			if(npc.nonceLife<=0){
				own.eatJisu++;
				SubmarineGameEngine.eatJisu=own.eatJisu;
				own.eatCount++;
				own.eatCount2++;
				own.scores += npc.scores;
				own.scores2 += npc.scores;
				SubmarineGameEngine.eatCount=own.eatCount;
				SubmarineGameEngine.eatCount2=own.eatCount2;
				SubmarineGameEngine.scores=own.scores;
				SubmarineGameEngine.scores2=own.scores2;
				CreateRole.npcs.removeElement(npc);
				CreateRole.count--;
			}
		}
	}
}
