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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class PhysicsCore extends TimerTask 
{
	static PhysicsCorePanel panel = new PhysicsCorePanel();
	public void init(String[] args)
	{
		JFrame frame = new JFrame("Physics");
		frame.setSize(400,400);
		frame.setLocation(100,50);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(panel);
		frame.setVisible(true);
		Timer timer = new Timer();
		timer.schedule(this, 0, 0110);
		frame.addMouseListener(panel);
		frame.addKeyListener(panel);
	}
	public static void main(String[] args)
	{
		PhysicsCore driver = new PhysicsCore();
		driver.init(args);
	}
	public void run()
	{  
		panel.repaint();
	}

	public static double angle(int y1, int x1, int y2, int x2)
	{
		double angle = Math.atan2(y1 - y2, x1 - x2);
		angle = (angle < 0) ? angle += 2* Math.PI : angle;
		return angle;
	}

	@SuppressWarnings("serial")
	public static class PhysicsCorePanel extends JPanel implements MouseListener, KeyListener
	{
		Object1D rocket = new Object1D(50,50, 25);
		Object1D rocket2 = new Object1D(600, 300, 25);
		Object1D rocket3 = new Object1D(400, 500, 25);

		Point gravLoc = new Point(250, 350);
		int updates = 0;

		LinkedList<Point> path = new LinkedList<Point>();

		Force grav = new Force(25, 2, 0); //M, A, rot
		Force applied = new Force(25, 2, 0);
		Force grav2 = new Force(1, 2, 0);

		Force r3Grav = new Force(1, 2, 0);
		Force r2Grav = new Force(1, 2, 0);
		Force r1Grav = new Force(1, 2, 0);

		int trailLength = 100;

		public void paintComponent(Graphics g)
		{
			g.setColor(Color.lightGray);
			g.fillRect(0, 0, getWidth(), getHeight());

			r3Grav.type = Force.GRAVITY;
			r2Grav.type = Force.GRAVITY;
			r1Grav.type = Force.GRAVITY;

			r3Grav.loc = rocket3.loc;
			r2Grav.loc = rocket2.loc;
			r1Grav.loc = rocket.loc;

			if(updates > 0)
			{

				if(rocket.forces.contains(applied) && updates > 10)
					rocket.forces.remove(applied);
				g.setColor(Color.red);
				int count = 0;
				for(int i = path.size()-2; i > 0; i--)
				{
					if(count > trailLength)
					{
						path.remove(i);
						continue;
					}
					int x1 = path.get(i).x;
					int y1 = path.get(i).y;
					int x2 = path.get(i + 1).x;
					int y2 = path.get(i + 1).y;

					g.drawLine(x1, y1, x2, y2);
					count++;
				}
			}
			g.setColor(Color.black);
			rocket.draw(g);
			rocket.update();

			//rocket2.draw(g);
			//rocket2.update();

			//rocket3.draw(g);
			//rocket3.update();

			//System.out.println(rocket.loc);

			if(updates == 0)
			{
				grav.loc = gravLoc;
				grav.type = Force.GRAVITY;

				grav2.loc = new Point(600, 350);
				grav2.type = Force.GRAVITY;

				rocket.forces.add(grav2);
				rocket.forces.add(grav); //TODO: add

				rocket.forces.add(applied);


				//rocket.forces.add(r2Grav);
				//rocket.forces.add(r3Grav);

				rocket2.forces.add(r1Grav);
				rocket2.forces.add(r3Grav);

				rocket3.forces.add(r1Grav);
				rocket3.forces.add(r2Grav);

				path.add(new Point(50, 50));
				//rocket.velocity.x = 10;
				//rocket.acceleration.x = 10;
			}
			path.add(new Point(rocket.loc.x, rocket.loc.y));



			//System.out.println(rocket.velocity);
			g.setColor(Color.yellow);
			g.fillOval(gravLoc.x-25, gravLoc.y-25, 50, 50);
			g.fillOval(grav2.loc.x-25, grav2.loc.y-25, 50, 50);

			/*
			int rx = rocket.loc.x - 25;
			int ry = rocket.loc.y - 25;
			int gx = (int) (Math.cos(rocket.rotation) * 100) + rx;
			int gy = (int) (Math.sin(rocket.rotation) * 100) + ry;

			g.drawLine(rx, ry, gx, gy);
			 */
			updates++;
		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}
		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == 38)
			{
				Force a = new Force(20, 2, 0);
				a.type = Force.APPLIED;
				rocket.forces.add(a);
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}
	}

}