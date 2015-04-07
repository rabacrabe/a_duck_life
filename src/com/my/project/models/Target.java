package com.my.project.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.my.project.models.targets.Bomb;
import com.pxr.basegame.Globale;
import com.pxr.basegame.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;

public abstract class Target {
	
	
	/*private	Bitmap targetBitmap;
	private	Bitmap targetEndBitmap;
	*/
	private	Bitmap targetOnFireBitmap;
	protected int nb_max_life = 0;
	private int id;
	protected int width = 0;
	protected int height = 0;
	protected int pos_x;
	protected int pos_y;
	private Context myContext;
	protected List<ColorTarget> listColorTarget;
	protected boolean onFire = false;
	private int life;
	private int angle = 0;
	protected boolean dead = false;
	protected boolean toDel = false;
	protected Matrix matrix = new Matrix();
	protected int screenWidth;
	protected int screenHeight;
	protected int score = 0;
	 protected Typeface tf;
	
	public Target(Context context, int targetNum, int newid) {
		myContext = context;
		
		id=newid;
		
		targetOnFireBitmap = BitmapFactory.decodeResource(context.getResources(),  R.drawable.target_icon_on_fire);
		tf =  Typeface.createFromAsset(context.getAssets(), Globale.CUSTOM_FONT);
		tf = tf.create(tf, Typeface.BOLD);
		
		pos_y = 0;

		
	}
	
	public abstract void	drawTarget(Canvas canvas, int screen_width, int screen_height, boolean isOnFire);
	public abstract HashMap<String, Integer>	shootBomb();
	
	
	protected void drawViseur(Canvas canvas) {
		
		Matrix matrix = new Matrix();
    	matrix.postTranslate(pos_x+2, pos_y+2);
    	targetOnFireBitmap = targetOnFireBitmap.createScaledBitmap(targetOnFireBitmap, width-5, height-5, true);
    	canvas.drawBitmap(targetOnFireBitmap, matrix, null);
		
		/*
		Paint paint = new Paint();
    	paint.setColor(Color.WHITE);
    	paint.setTextSize(20);
    	
    	paint.setStyle(Paint.Style.STROKE);
    	
    	canvas.drawCircle(pos_x+(width/2), pos_y+(height/2), width/2, paint);
    	
    	canvas.drawLine(pos_x+(width/2), pos_y-10, pos_x+(width/2), pos_y+10, paint);
		*/
    	
    	/*
		canvas.drawLine(pos_x-5, pos_y-5, pos_x+width+5, pos_y-5, paint);
		canvas.drawLine(pos_x+width+5, pos_y-5, pos_x+width+5, pos_y+height+5, paint);
		canvas.drawLine(pos_x+width+5, pos_y+height+5, pos_x-5, pos_y+height+5, paint);
		canvas.drawLine(pos_x-5, pos_y+height+5, pos_x-5, pos_y-5, paint);
		*/
	}
	
	protected void initColorTarget(int num) {
		listColorTarget = Collections.synchronizedList(new ArrayList<ColorTarget>());
		
		for (int i = 0; i < num; i++) {
			int random = (int)(Math.random() * (Globale.NB_COLOR_BUTTONS)) + 0;
			listColorTarget.add(new ColorTarget(myContext, random));
		}
		
		life = listColorTarget.size();
	}

	public boolean verifShot(int colorID) {
		synchronized (listColorTarget) {			
			if (life > 0 && listColorTarget.get(0).getColor_id() == colorID) {
				listColorTarget.remove(0);
				life--;
				//onFire = true;
				
				if (life <= 0) {
					//onFire = false;
					dead = true;
				}
				return true;
			}
		}
		return false;
	}
	
	/*
	 * on verifie que l'on a ou non selectionné une nouvelle cible
	 */
	public	boolean isTouch(int x, int y) {
		
		if (x >= pos_x && x <= pos_x+width && y >= pos_y && y <= pos_y+height)
			return true;
		
		return false;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getPos_x() {
		return pos_x;
	}

	public void setPos_x(int pos_x) {
		this.pos_x = pos_x;
	}

	public int getPos_y() {
		return pos_y;
	}

	public void setPos_y(int pos_y) {
		this.pos_y = pos_y;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean GetOnFire() {
		return onFire;
	}

	
	public void setOnFire(boolean onFire) {
		this.onFire = onFire;
	}

	public boolean GetIsToDel() {
		return toDel;
	}

	public void setToDel(boolean toDel) {
		this.toDel = toDel;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
}
