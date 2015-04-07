package com.my.project.models.ducks;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.my.project.models.ColorTarget;
import com.my.project.models.Target;
import com.pxr.basegame.Globale;
import com.pxr.basegame.R;

public class Basic_Duck extends Target{

	
	private int nb_max_life = Globale.LIFE_SMALL;
	private int speed = Globale.SPEED_SLOW;
	private int angle = 0;
	private	Bitmap targetBitmap;
	private	Bitmap targetBitmap2;
	private	Bitmap targetEndBitmap;
	private boolean avance = false;
	private int timer_move = 0;
	
	
	public Basic_Duck(Context context, int id) {
		super(context, 0, id);
		
		targetBitmap = BitmapFactory.decodeResource(context.getResources(),  R.drawable.duck_basic_icon4);
		targetBitmap2 = BitmapFactory.decodeResource(context.getResources(),  R.drawable.duck_basic_icon3);
		score = Globale.SCORE_BASIC_DUCK;
		
		targetEndBitmap = BitmapFactory.decodeResource(context.getResources(),  R.drawable.explosion);
	
		initColorTarget(nb_max_life);
		
	}

	public void	drawTarget(Canvas canvas, int screen_width, int screen_height, boolean isOnFire) {
		Matrix matrix = new Matrix();
    		
    	if (width == 0 || height == 0) {
    		width = screen_width/Globale.SIZE_SMALL;//targetBitmap.getWidth();
    		height = (width*targetBitmap.getHeight())/targetBitmap.getWidth();
    		targetBitmap = targetBitmap.createScaledBitmap(targetBitmap, width, height, true);
    		
    		targetBitmap2 = targetBitmap2.createScaledBitmap(targetBitmap2, width, height, true);
    		//targetBitmap2 = targetBitmap2.createScaledBitmap(targetBitmap2, width, height, true);
    		//targetBitmap3 = targetBitmap3.createScaledBitmap(targetBitmap3, width, height, true);
    		targetEndBitmap = targetEndBitmap.createScaledBitmap(targetEndBitmap, width, height, true);
    		
    		int random = (int)(Math.random() * ((screen_height-(screen_height/Globale.SIZE_FRONT_BOTTOM)-height-20)-0)) + (screen_height/Globale.SIZE_HEART_LIFE);
    		pos_y = random;
    	}
    	
    	matrix.setRotate(angle, width/2, height/2);
    	matrix.postTranslate(pos_x, pos_y);

    	if (pos_x == 0 || pos_x > screen_width) {
    		pos_x=0;
    	}
    	
    	pos_x+=Globale.SPEED_SLOW;
    	
    
    	if (dead == false) {
    	//	if (pos_y%10==0) {
	    //		int random = (int)(Math.random() * (2-0)) + 0;
	    //		switch(random){
	    //			case 0:
	    //				targetBitmapRemember = targetBitmap;
    		
    		if (avance == false) {
    			canvas.drawBitmap(targetBitmap, matrix, null);
    			timer_move++;
    			if (timer_move > 10) {
    				avance = true;
    				timer_move = 0;
    			}
    		}
    		else {
    			canvas.drawBitmap(targetBitmap2, matrix, null);
    			timer_move++;
    			if (timer_move > 10) {
    				avance = false;
    				timer_move = 0;
    			}
    		}
    		
	    //				break;
	    //			case 1:
	    //				targetBitmapRemember = targetBitmap2;
	    //				canvas.drawBitmap(targetBitmap2, matrix, null);
	    //				break;
	    //			case 2:
	    //				targetBitmapRemember = targetBitmap3;
	    //				canvas.drawBitmap(targetBitmap3, matrix, null);
	    //				break;
	    //		}
    	//	}
    	//	else
    	//		canvas.drawBitmap(targetBitmapRemember, matrix, null);
    	}
    	else {
    		canvas.drawBitmap(targetEndBitmap, matrix, null);
    		Paint paint = new Paint();
    		 paint.setColor(Color.rgb(128, 51, 0));
    		   paint.setTextSize(height/3);
    		   paint.setTypeface(tf);
    		   
    		canvas.drawText("+"+ score, pos_x, pos_y-10, paint);
    		toDel = true;
    	}
    	
    	if (isOnFire == true) {
    		drawViseur(canvas);
    	}
    	
    	int i = 0;
    	for (ColorTarget color : listColorTarget) {
    		color.drawTarget(canvas, this, screen_width, screen_height, i);
    		i++;
    	}
    	//canvas.drawText("banane", pos_x, pos_y+height+10, paint);
	}

	@Override
	public HashMap<String, Integer> shootBomb() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
