import controlP5.*;
import processing.core.*;
import java.awt.*;

class Control {
	private PApplet parent;
	private ControlP5 controlP5;
	private ControlWindow cw;
	private int face_move_x, face_move_y, cam_size, brightness, contrast;
	
	Control(final PApplet parent) {
	  this.parent = parent;
      controlP5 = new ControlP5(this.parent);
	  
	}
	
	public void controlWindow() {
	  cw = controlP5.addControlWindow("win",300,500);
	  cw.setLocation(10,10);
	  groupFaceControl().moveTo(cw);
	  groupActionControl().moveTo(cw);
	  groupCamControl().moveTo(cw);
	  
	}
	
	private ControlGroup groupFaceControl() {
	  ControlGroup cg = controlP5.addGroup("faceControl",30,30);
	  controlP5.begin(cg,0,10);
	  controlP5.addSlider("faceMoveX",-30,30).linebreak();
	  controlP5.addSlider("faceMoveY",-30,30).linebreak();
	  controlP5.addButton("saveFaceMove");
	  controlP5.end();
	
	  controlP5.controller("faceMoveX").setValue(this.face_move_x);
	  controlP5.controller("faceMoveX").setLabel("face_move_X");
	  
	  controlP5.controller("faceMoveY").setValue(this.face_move_y);
	  controlP5.controller("faceMoveY").setLabel("face_move_y");
	
	  controlP5.controller("saveFaceMove").setLabel("Save");
	
	  return cg;
	  
	}
	
	private ControlGroup groupActionControl() {
	  ControlGroup cg = controlP5.addGroup("actionControl", 30,130);
	  controlP5.begin(cg,0,10);
	  controlP5.addToggle("stopMoving", false, 0, 10, 60, 20).setGroup(cg);
	  controlP5.addToggle("stopWater", false, 70, 10, 60, 20).setGroup(cg);
	  controlP5.addToggle("stopTimer", false, 140, 10, 60, 20).setGroup(cg);
	  controlP5.end();
	
	  controlP5.controller("stopMoving").setLabel("Stop Moving");
	  controlP5.controller("stopWater").setLabel("Stop Water");
	  controlP5.controller("stopTimer").setLabel("Stop Timer");
	
	  return cg;
	}
	
	private ControlGroup groupCamControl() {
	  ControlGroup cg = controlP5.addGroup("camControl", 30,260);
	  controlP5.begin(cg,0,10);
	  
	  controlP5.addSlider("setBrightness",-128, 128).linebreak();
	  controlP5.addSlider("setContrast",-128, 128).linebreak();
	  controlP5.addButton("saveBrightnessContrast");
	
	  controlP5.addToggle("testImage", false, 0, 80, 60, 20).setGroup(cg);
	  
	  Radio r = controlP5.addRadio("camSize", 70, 80);
	  r.setGroup(cg);
	  r.deactivateAll(); // use deactiveAll to not make the first radio button active.
	  r.add("320 x 240", 320);
	  r.add("640 x 480", 640);
	  r.add("800 x 600", 800);
	  r.add("1024 x 768", 1024);
	  
	  controlP5.end();
	  
	  controlP5.controller("camSize").setValue(this.cam_size);
	  controlP5.controller("testImage").setLabel("Test Image");
	  controlP5.controller("setBrightness").setValue(this.brightness);
	  controlP5.controller("setContrast").setValue(this.contrast);
	  controlP5.controller("saveBrightnessContrast").setLabel("Save");
	  return cg;
	}
	
	
	/* CONFIGS */
	public void setFaceMoveX(int face_move_x) {
		this.face_move_x = face_move_x;
	}
	
	public void setFaceMoveY(int face_move_y) {
		this.face_move_y = face_move_y;
	}
	
	public void setCamSize(int cam_size) {
		this.cam_size = cam_size;
	}
	
	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}
	
	public void setContrast(int contrast) {
		this.contrast = contrast;
	}
}
