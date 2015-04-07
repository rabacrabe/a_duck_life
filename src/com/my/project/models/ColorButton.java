package com.my.project.models;

import com.pxr.basegame.Globale;
import com.pxr.basegame.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class ColorButton {

	
	private	Bitmap buttonBitmap;
	private	Bitmap buttonTouchBitmap;
	private int	width;
	private int height;
	private int numero;
	private int pos_x;
	private int pos_y;
	private int pos_x_fin;
	private int pos_y_fin;
	private boolean touch = false;
	private int color;
	
	public ColorButton(Context context, int position) {
		
		switch (position) {
			case Globale.BLUE_ID:
				buttonBitmap = BitmapFactory.decodeResource(context.getResources(),  R.drawable.button_blue);
				buttonTouchBitmap = BitmapFactory.decodeResource(context.getResources(),  R.drawable.button_blue_touch);
				color = Globale.BLUE_ID;
				break;
			case Globale.GREEN_ID:
				buttonBitmap = BitmapFactory.decodeResource(context.getResources(),  R.drawable.button_green);
				buttonTouchBitmap = BitmapFactory.decodeResource(context.getResources(),  R.drawable.button_green_touch);
				color = Globale.GREEN_ID;
				break;
			case Globale.ORANGE_ID:
				buttonBitmap = BitmapFactory.decodeResource(context.getResources(),  R.drawable.button_orange);
				buttonTouchBitmap = BitmapFactory.decodeResource(context.getResources(),  R.drawable.button_orange_touch);
				color = Globale.ORANGE_ID;
				break;
			case Globale.PURPLE_ID:
				buttonBitmap = BitmapFactory.decodeResource(context.getResources(),  R.drawable.button_purple);
				buttonTouchBitmap = BitmapFactory.decodeResource(context.getResources(),  R.drawable.button_purple_touch);
				color = Globale.PURPLE_ID;
				break;
			case Globale.RED_ID:
				buttonBitmap = BitmapFactory.decodeResource(context.getResources(),  R.drawable.button_red);
				buttonTouchBitmap = BitmapFactory.decodeResource(context.getResources(),  R.drawable.button_red_touch);
				color = Globale.RED_ID;
				break;
		}
		numero = position;
		
	}
	
	public void	drawButton(Canvas canvas, int screen_width, int screen_height) {
		Matrix matrix = new Matrix();
		//int width_to_app = 
		
		if (width == 0 || height == 0) {			
			width = (screen_width/Globale.NB_COLOR_BUTTONS)-10;
			height = (screen_height/Globale.SIZE_HEIGHT_BUTTON);

			pos_x= ((screen_width/Globale.NB_COLOR_BUTTONS)*numero)+5;
			pos_y = screen_height-height-10;
			
			pos_x_fin = ((screen_width/Globale.NB_COLOR_BUTTONS)-10) + pos_x;
			pos_y_fin = height + (screen_height-height-10);

		}
		
		buttonBitmap = buttonBitmap.createScaledBitmap(
				buttonBitmap, width, height, true);
		
		buttonTouchBitmap = buttonTouchBitmap.createScaledBitmap(
				buttonTouchBitmap, width, height, true);
		
		
    	matrix.postTranslate(pos_x, pos_y);
    	
    	if (touch == false)	
    		canvas.drawBitmap(buttonBitmap, matrix, null);
    	else {
    		canvas.drawBitmap(buttonTouchBitmap, matrix, null);
    		touch = false;
    	}
	}
	
	public	boolean	VerifTouchPosition(int touch_x, int touch_y) {
		if (touch_x >= pos_x && touch_x <= pos_x_fin &&
				touch_y >= pos_y-(height/2) && touch_y <= pos_y_fin+(height/2)) {
			touch = true;
			return true;
		}
		return false;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
}
