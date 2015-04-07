package com.my.project.models;

import com.pxr.basegame.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class ColorTarget {
	
	private static final int BLUE_ID = 0;
	private static final int GREEN_ID = 1;
	private static final int ORANGE_ID = 2;
	private static final int PURPLE_ID = 3;
	private static final int RED_ID = 4;
	
	
	private	Bitmap targetBitmap;
	private int width;
	private int height;
	private int color_id = 1;
	
	public ColorTarget(Context context, int numero) {
		switch (numero) {
			case 0:
				targetBitmap = BitmapFactory.decodeResource(context.getResources(),  R.drawable.target_blue);
				color_id = BLUE_ID;
				break;
			case 1:
				targetBitmap = BitmapFactory.decodeResource(context.getResources(),  R.drawable.target_green);
				color_id = GREEN_ID;
				break;
			case 2:
				targetBitmap = BitmapFactory.decodeResource(context.getResources(),  R.drawable.target_orange);
				color_id = ORANGE_ID;
				break;
			case 3:
				targetBitmap = BitmapFactory.decodeResource(context.getResources(),  R.drawable.target_purple);
				color_id = PURPLE_ID;
				break;
			case 4:
				targetBitmap = BitmapFactory.decodeResource(context.getResources(),  R.drawable.target_red);
				color_id = RED_ID;
				break;
		}
		
		width = targetBitmap.getWidth();
		height = targetBitmap.getHeight();
		
	}
	
	public void	drawTarget(Canvas canvas, Target target, int screen_width, int screen_height, int position) {
		width = (screen_width/40);
		height = (screen_width/40);
		targetBitmap = targetBitmap.createScaledBitmap(
				targetBitmap, width, height, true);
		
		Matrix matrix = new Matrix();
		matrix.postTranslate(target.getPos_x()+((position*width)+2), target.getPos_y()+target.getHeight()+5);
		
		canvas.drawBitmap(targetBitmap, matrix, null);
	}

	public int getColor_id() {
		return color_id;
	}

	public void setColor_id(int color_id) {
		this.color_id = color_id;
	}
}
