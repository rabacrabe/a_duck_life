package com.my.project.models;

import com.pxr.basegame.Globale;
import com.pxr.basegame.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class HeartLife {

	private Bitmap lifeBitmap;
	private int width = 0;
	private int height = 0;
	private int pos_x = 0;
	private int pos_y = 0;
	private int num;
	
	public	HeartLife(Context context, int position) {
		lifeBitmap = BitmapFactory.decodeResource(context.getResources(),  R.drawable.life_icon);
		num = position;
	}
	
	public	void	DrawIt(Canvas canvas, int screen_width, int screen_height) {
		Matrix matrix = new Matrix();
		
		if (width == 0 || height == 0) {
			height = screen_height/Globale.SIZE_HEART_LIFE;
			width = (height*lifeBitmap.getWidth())/lifeBitmap.getHeight();
    		
    		lifeBitmap = lifeBitmap.createScaledBitmap(lifeBitmap, width, height, true);
    		pos_y = 5;
    		pos_x = ((num + 1)* (width/2)) - 2;
		}
		matrix.postTranslate(pos_x, pos_y);
		canvas.drawBitmap(lifeBitmap, matrix, null);
	}
	
}
