import hypermedia.video.*;
import processing.core.*;
import java.awt.*;

class CvDetector
{
  
  private OpenCV opencv;
  private PImage maskImage;
  private int face_w, face_h;
  private boolean faceImage = false;
  private Rectangle[] faces;
  public int face_move_x, face_move_y, brightness, contrast;  
  private PApplet model;
  
  CvDetector(final PApplet model)
  {
    this.model = model;
    this.opencv = new OpenCV(model);
    this.maskImage = model.loadImage("litemask3.png");
   }
  
  public void init(int cam_w, int cam_h) {
	
	this.opencv.capture( cam_w, cam_h );
	//this.opencv.loadImage( "face_4.jpg");

	this.opencv.cascade( OpenCV.CASCADE_FRONTALFACE_ALT_TREE );
    
	this.opencv.convert( this.model.GRAY );
	
	this.opencv.brightness(this.brightness);
	this.opencv.contrast(this.contrast);
  }
  
  public void faceSize(int w, int h) {
	this.face_w = w;
	this.face_h = h;
  }
  
  public void initDetect() {
	this.opencv.read();
	
	this.faces();
  }
  
  
  public PImage image() {
	return this.drawImage();
  }
  
  public PImage testImage() {
	 if (haveFaces()) {
		return this.getImage(opencv.image());

	} else {
		return new PImage();

    }
  }
  
  private void faces() {
	this.faces = opencv.detect(1.2f, 2, 0, 40, 40);
  }
  
  
  public boolean haveFaces() {
	return this.faces.length > 0 ? true : false;
  }
  
  public PImage drawImage() {
	
	if (haveFaces()) {
		return this.imageWithOptions(opencv.image());
		
	} else {
		return new PImage();
		
    }
	
  }

  /* CONTROLS */
  public void setFaceMoveX(int v){
	this.face_move_x = v;
  }

  public void setFaceMoveY(int v){
	this.face_move_y = v;
  }
  
  public void setBrightness(int v){
	this.brightness = v;
	this.opencv.brightness(v);
	
  }

  public void setContrast(int v){
	this.contrast = v;
	this.opencv.contrast(v);
  }
  /* END CONTROLS */

  private PImage getImage(PImage img) {
	PImage processedImage = new PImage();
	processedImage = img.get(this.faces[0].x+this.face_move_x, this.faces[0].y+this.face_move_y, this.faces[0].width, this.faces[0].height);
	return processedImage;
  }
  
  private PImage imageWithOptions(PImage img) {
	PImage processedImage = this.getImage(img);
	
    processedImage.resize(this.face_w, this.face_h);
	processedImage.mask(this.maskImage);
	
	return processedImage;
  }
 
 
}

