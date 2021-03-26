package com.tedu.element;

import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

public class Die extends ElementObj{
	
	private ElementManager em = ElementManager.getManager();
	List<ElementObj> map = em.getElementsByKey(GameElement.MAPS);
	private String type;
	private long DieTime = 0; 
	private int updateNum = 0;
	private String DieKey = null;
	private int mapX = 0;//地图x值
	private int mapV = 3;//地图移动速度
	
	public Die() {
		
	}
	
	public Die(int x, int y, int w, int h, ImageIcon icon,String DieKey, String type) {
		super(x,y,w,h,icon);
		this.DieKey = DieKey;
		this.type = type;
	}
	
	@Override
	public void showElement(Graphics g) {
		if(this.type == "enemy") {
			g.drawImage(this.getIcon().getImage(), 
					this.getX()+10, this.getY()+10, 
					this.getW()-25, this.getH()-12, null);
		}
		if(this.type == "hostage") {
			g.drawImage(this.getIcon().getImage(), 
					this.getX(), this.getY(), 
					this.getW()+10, this.getH()+10, null);
		}
		if(this.type == "tank"){
			   g.drawImage(this.getIcon().getImage(), 
			     this.getX(), this.getY(), 
			     this.getW(), this.getH(), null);
			  }
	}
	
	@Override
	protected void move(long gameTime) {
		if(!map.isEmpty()) {
			int x = map.get(0).getX();
		    if(this.mapX != x) {
		    	this.mapX = x;
		    	switch(((Maps)map.get(0)).getFx()) {
		    	case "left":
		    		this.setX(this.getX() + this.mapV);
		    		break;
		    	case "right":
		    		this.setX(this.getX() - this.mapV);
		    		break;
		    	default:
		    		break;
		    	}
		    }
		}
		if(this.type == "hostage" && ((this.updateNum >=6 && this.updateNum <=10) || this.updateNum >=18)) {// 
			this.setX(this.getX()-3);
		}
	}
	
	@Override
	protected void updateImage(long gameTime) {
		if(gameTime - this.DieTime > 20) {
			this.DieTime =  gameTime;
			List<ImageIcon> listImageIcon1 = GameLoad.imgMap_element.get(this.DieKey);
			
			if(this.updateNum>listImageIcon1.size()-1) {
				if(this.type == "enemy") {
					this.updateNum=0;
					this.setLive(false);
					return;
				}
				if(this.type == "hostage") {
					this.updateNum=18;
					if(this.getX() < 0) {
						this.updateNum=0;
						this.setLive(false);
						return;
					}
				}
				if(this.type == "tank") {
				      this.updateNum=0;
				      this.setLive(false);
				      return;

				}
			}
			if(updateNum == 15 && this.type == "hostage") {
				String hostageStr = (this.getX()+this.getIcon().getIconWidth()/2)+","+(this.getY()+this.getIcon().getIconHeight()/2)+",Apple,5";
				ElementObj obj=GameLoad.getObj("prop");
				ElementObj emeny = obj.createElement(hostageStr);
				em.addElement(emeny, GameElement.PROP);
			}
			ImageIcon icon=listImageIcon1.get(updateNum++);
			this.setIcon(icon);
		}
	}
	
	

}
