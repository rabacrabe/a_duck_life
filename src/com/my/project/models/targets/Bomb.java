package com.my.project.models.targets;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.my.project.models.ColorTarget;
import com.my.project.models.Target;
import com.pxr.basegame.Globale;
import com.pxr.basegame.R;

public class Bomb extends Target{


	private	Bitmap targetBitmap;
	private	Bitmap targetEndBitmap;
	private int angle = 0;
	
	public Bomb(Context context, int id) {
		super(context, 0, id);
		targetBitmap = BitmapFactory.decodeResource(context.getResources(),  R.drawable.bomb_icon);
		targetEndBitmap = BitmapFactory.decodeResource(context.getResources(),  R.drawable.explosion);
		
		nb_max_life = Globale.LIFE_SMALL;
		initColorTarget(nb_max_life);
	}


	public void	drawTarget(Canvas canvas, int screen_width, int screen_height, boolean isOnFire) {
		Matrix matrix = new Matrix();
		matrix.setRotate(angle, width/2, height/2);
    	matrix.postTranslate(pos_x, pos_y);
    		
    	if (width == 0 || height == 0) {
    		screenWidth = screen_width;
    		screenHeight = screen_height;
    		width = screen_width/Globale.SIZE_SMALL;
    		height = (width*targetBitmap.getHeight())/targetBitmap.getWidth();
    		targetBitmap = targetBitmap.createScaledBitmap(targetBitmap, width, height, true);
    		//targetBitmap2 = targetBitmap2.createScaledBitmap(targetBitmap2, width, height, true);
    		//targetBitmap3 = targetBitmap3.createScaledBitmap(targetBitmap3, width, height, true);
    		targetEndBitmap = targetEndBitmap.createScaledBitmap(targetEndBitmap, width, height, true);
    		
    		int random = (int)(Math.random() * ((screen_height-Globale.MARGIN_BOTTOM_HEIGHT-height-20)-0)) + 0;
    		pos_y = random;
    	}
    /*	
    	if (pos_y == 0 || pos_y > screen_height) {
    		pos_y=0;
    	}
    	
    	pos_y+=SPEED_FAST;
    	*/
    	
    	if (pos_x == 0 || pos_x > screen_width) {
    		pos_x=0;
    	}
    	
    	pos_x+=Globale.SPEED_FAST;
    	
    	
		angle+=10;
    	
    	if (angle >= 360) {
    		angle = 0;
    	}
    
    	if (dead == false) {
			canvas.drawBitmap(targetBitmap, matrix, null);
    	}
    	else {
    		canvas.drawBitmap(targetEndBitmap, matrix, null);
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
	
	public HashMap<String, Integer>	shootBomb() {
		return null;
	}
	
}
