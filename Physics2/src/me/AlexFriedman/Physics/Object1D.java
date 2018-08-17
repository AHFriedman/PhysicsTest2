package me.AlexFriedman.Physics;

/*
 * MIT License

Copyright (c) 2018 AHFriedman

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.LinkedList;

public class Object1D 
{
	
	public Point loc;
	public Point acceleration = new Point();
	public Point velocity = new Point();
	public double mass;

	public LinkedList<Force> forces = new LinkedList<Force>();
	
	public double timeNoMovement = 0;

	public double rotation = 0;

	public boolean drawForces = true;
	
	public Object1D(Point loc, double mass)
	{
		this.loc = loc;
		this.mass = mass;
	}
	public Object1D(int x, int y, double mass)
	{
		this(new Point(x, y), mass);
	}

	public void update()
	{
		double calcAngle = 0;
		double gravSorces = 0;
		
		for(int i = 0; i < forces.size(); i++)
		{
			Force tmp = forces.get(i);
			if(tmp.type == Force.GRAVITY)
			{
				 calcAngle += PhysicsCore.angle(tmp.loc.y, tmp.loc.x, loc.y, loc.x);
				 gravSorces++;
			}
		}
		//System.out.println(calcAngle + "/");
		rotation = calcAngle / gravSorces; //TODO: MAKE VALID IF THEY ARE 180 APART! ( OR PI)
		Double rotVerify = rotation;
		if(rotVerify.isNaN())
			rotation = -Math.PI/2;
		
		
		double EaX = 0;
		double EaY = 0;
		for(int i = 0; i < forces.size(); i++)
		{
			// F = ma
			Force f = forces.get(i);
			double angle = (f.type == Force.GRAVITY) ? rotation : rotation + Math.PI/2 + f.angle;
			EaX += f.forceNewtons() * Math.cos(angle);
			EaY += f.forceNewtons() * Math.sin(angle); 
			//System.out.println(forces.size() );
		}
		acceleration.setLocation(EaX/mass, EaY/mass);
		
		if(acceleration.x == 0 && acceleration.y == 0)
			timeNoMovement = 0;


		double aX = velocity.getX() + (acceleration.getX() * 1);
		double aY = velocity.getY() + (acceleration.getY() * 1);
		
		/*
		if(timeNoMovement != 0)
		{
			double preX = acceleration.x/Math.abs(acceleration.x);
			double preY = acceleration.y/Math.abs(acceleration.y);
			double postX = aX/Math.abs(aX);
			double postY = aY/Math.abs(aY);

			if(preX * -1 == postX && preY * -1 == postY)
				timeNoMovement = 0;
		}
		*/
		this.velocity.setLocation(aX, aY);

		double nextX = loc.x + this.velocity.x ;
		double nextY = loc.y + this.velocity.y;
		loc.setLocation(nextX, nextY);

		timeNoMovement++;


	}

	public void draw(Graphics g)
	{
		int x = (int) (loc.x - mass/2);
		int y = (int) (loc.y - mass/2);
		int x2 = (int) (mass);
		int y2 = (int) (mass);
		g.fillOval(x, y, x2, y2);
		
		if(drawForces)
		{
			int cx = x + (int)mass/2;
			int cy = y + (int)mass/2;
			for(Force f : forces)
			{
				
				//double calcAngle = ;
				double angle = (f.type == Force.GRAVITY) ? PhysicsCore.angle(f.loc.y, f.loc.x, loc.y, loc.x) : rotation + Math.PI/2 + f.angle;
				int fx = (int) (Math.cos(angle) * 100) + cx;
				int fy = (int) (Math.sin(angle) * 100) + cy;
				
				g.setColor(Color.orange);
				g.drawLine(cx, cy, fx, fy);
				//System.out.println(angle);
			}
			
			//g.setColor(Color.blue);
			//g.drawLine(x, y, acceleration.x * 10 + x, acceleration.y * 10 + y);
			
		}
	}
}
