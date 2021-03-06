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
import java.awt.Point;

public class Force 
{
	public static final byte GRAVITY = 1;
	public static final byte APPLIED = 2;
	public static final byte FRICTION = 3;
	public static final byte NORMAL = 4;
	
	
	public double mass;
	public double a;
	public double angle;
	public double time;
	public byte type = -1;
	public Point loc;
	
	public Force(double m, double a, double angle)
	{
		this.mass = m;
		this.a = a;
		this.angle = angle;
	}
	
	public double forceNewtons()
	{
		return mass * a;
	}
}
