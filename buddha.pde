import hypermedia.video.*;
import processing.opengl.*;
import fullscreen.*; 
import controlP5.*;
import proxml.*;
import proxml.XMLElement;

CvDetector opencv;
Motion motion;
Water water;
Control control;
ConfigParser config;
PImage bg, buddha_with_face, buddha_without_face, buddha, img;
//FullScreen fs;

int face_x_def = 613;
int face_y_def = 167;
int buddha_x_def = 400;
int buddha_y_def = 90;


int buddha_w = 548;
int buddha_h = 674;
int buddha_x = buddha_x_def;
int buddha_y = buddha_y_def;

int face_w = 127;
int face_h = 133;
int face_x = face_x_def;
int face_y = face_y_def;
int startingTime;
boolean touch_bottom = false;
boolean touch_top = false;
boolean stop_moving, stop_water, stop_timer, test_image = false;
//boolean stop_water = false;

void setup() 
{

  config = new ConfigParser(this);
  

  size(1024, 768, OPENGL);
  frameRate(30);
  
  bg = loadImage("bg2.jpg");
  
  buddha_without_face = loadImage("buddha_without_face_4.png");
  buddha_with_face = loadImage("buddha_with_face_d.png");
  
  
  opencv = new CvDetector(this);
  motion = new Motion(this, buddha_w, buddha_h);
  water = new Water(this);
  control = new Control(this); 
  
  
  opencv.faceSize(face_w, face_h);
  startingTime = millis();
  setupConfig();
  //fs = new FullScreen(this);
  //fs.enter();
}

void setupConfig() {
   try 
   { 
	opencv.init(config.getIntConfig("cam_w"), config.getIntConfig("cam_h"));
	opencv.setFaceMoveX(config.getIntConfig("face_move_x"));
	opencv.setFaceMoveY(config.getIntConfig("face_move_y"));
	opencv.setBrightness(config.getIntConfig("brightness"));
	opencv.setContrast(config.getIntConfig("contrast"));
	
	control.setFaceMoveX(config.getIntConfig("face_move_x"));
	control.setFaceMoveY(config.getIntConfig("face_move_y"));
	control.setCamSize(config.getIntConfig("cam_w"));
	control.setBrightness(config.getIntConfig("brightness"));
	control.setContrast(config.getIntConfig("contrast"));
	
   } catch(NullPointerException e) { e.printStackTrace(); }
   control.controlWindow();
}


/* CONTROLLERS */

public void faceMoveX(int v) { 
  opencv.setFaceMoveX(v);
}

public void faceMoveY(int v) { 
  opencv.setFaceMoveY(v);
}

public void saveFaceMove() { 
  config.saveConfig("face_move_x", opencv.face_move_x);
  config.saveConfig("face_move_y", opencv.face_move_y);
}

public void stopMoving(boolean v) {
	stop_moving = v;
}

public void stopTimer(boolean v) {
	stop_timer = v;
}

public void stopWater(boolean v) {
	stop_water = v;
}

public void testImage(boolean v) {
	test_image = v;
}
public void camSize(int v) {
  
  int cam_w = 0;
  int cam_h = 0;

  switch (v) {
  case 320: 
    cam_w = 320;
    cam_h = 240;
    break;
  case 640: 
    cam_w = 640;
	cam_h = 480;
	break;
  case 800: 
    cam_w = 800;
	cam_h = 600;
	break;
  case 1024: 
    cam_w = 1024;
	cam_h = 768;
	break;
  }

  config.saveConfig("cam_w", cam_w);
  config.saveConfig("cam_h", cam_h);
}

public void setBrightness(int v) {
	opencv.setBrightness(v);
}

public void setContrast(int v) {
	opencv.setContrast(v);
}

public void saveBrightnessContrast() {
	config.saveConfig("brightness", opencv.brightness);
	config.saveConfig("contrast", opencv.contrast);
}

/* END CONTROLLERS */

void move(int x, int y) 
{
	face_x = x+(face_x_def-buddha_x_def);
	face_y = y+(face_y_def-buddha_y_def);
	buddha_x = x;
	buddha_y =y;
}

void draw() 
{
   
   int index = (millis() - startingTime) / 1000;
   int motion_x = motion.x();
   int motion_y = motion.y();
   

   background(bg);
   motion.objMotion();
   
   if (!stop_moving) {
    move(motion_x, motion_y);
   }
   
   

   if (index%10==0 || stop_timer) {
	  opencv.initDetect();
   	  img = opencv.image();
 	  
      if (test_image) {
		image(opencv.testImage(), 0, 0, 200, 300);
	  }
   }
   
   blend(img, 0, 0, face_w, face_h, face_x, face_y, face_w, face_h, OVERLAY);
   buddha = opencv.haveFaces() ? buddha_without_face : buddha_with_face;
   blend(buddha, 0, 0, buddha_w, buddha_h, buddha_x, buddha_y, buddha_w, buddha_h, SCREEN);
   
   if (!stop_water) {
     draw_water(motion_x, motion_y);
   }
 
}

void draw_water(int motion_x, int motion_y) {
   	
	//water.disturb(motion_x+parseInt(random(buddha_w/2-10, buddha_w/2+10)), parseInt(random(0, 10)));

	if (motion_y == height-buddha_h) touch_bottom = true;
	if (motion_y + 40 < height - buddha_h) touch_bottom = false;
	
	if (motion_y == 0) touch_top = true;
	if (motion_y - 20 > 0) touch_top = false;
	
		if(touch_top) {
			water.disturb(motion_x+parseInt(random(buddha_w/2-1, buddha_w/2+1)), parseInt(random(0, 10)));
		}
		
		if(touch_bottom) {
			water.disturb(motion_x+parseInt(random(buddha_w/2-4, buddha_w/2+4)), parseInt(random(height, height-40)));
			//water.disturb(parseInt(random(motion_x+40, (motion_x+buddha_w)-40)), parseInt(random(height, height-100)));
		}
	

	  water.draw();
	

}

void xmlEvent(XMLElement xmlElement) {
	config.xmlEvent(xmlElement);
}
