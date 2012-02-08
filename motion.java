import processing.core.*;
import java.awt.*;

class Motion
{
	private int obj_x;
	private int obj_y;
	private int obj_dir = -4;
	private int obj_w;
	private int obj_h;
	private PApplet model;

	private float dy = 2;  // Direction 

	Motion(final PApplet model, int obj_w, int obj_h) {
		this.model = model;
		this.obj_w = obj_w;
		this.obj_h = obj_h;
		
	}

	public int x() {
		return this.obj_x;
	}

	public int y() {
		return this.obj_y;
	}

	public void objMotion() {
		this.obj_x += this.obj_dir * model.random(0,1);
		this.obj_y += this.dy;
		if(this.obj_x > this.model.width+this.obj_w) {
			this.obj_x = -this.model.width/2 - this.obj_w;
            
            this.obj_y = this.model.parseInt(this.model.random(0, this.model.height));
			this.dy = 0;
		}
		
		// If ball hits paddle or back wall, reverse direction
		if(this.obj_x < 0) {
			this.obj_dir *= -1;
		}
		
		if(this.obj_x+this.obj_w > this.model.width) {
			this.obj_dir *= -1;
		}
		
		// If the obj is touching top or bottom edge, reverse direction
		if(this.obj_y > this.model.height-obj_h) {
			this.dy = this.dy * -1;
		}
		
		if(this.obj_y < 0) {
			this.dy = this.dy * -1;
		}
	}
}

