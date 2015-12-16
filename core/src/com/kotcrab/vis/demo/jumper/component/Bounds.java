package com.kotcrab.vis.demo.jumper.component;

import com.artemis.Component;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/** @author Kotcrab */
public class Bounds extends Component {
	public Rectangle bounds = new Rectangle();

	public Rectangle set (float x, float y, float width, float height) {
		return bounds.set(x, y, width, height);
	}

	public float getY () {
		return bounds.getY();
	}

	public Vector2 getCenter (Vector2 vector) {
		return bounds.getCenter(vector);
	}

	public Rectangle fitInside (Rectangle rect) {
		return bounds.fitInside(rect);
	}

	public Rectangle setWidth (float width) {
		return bounds.setWidth(width);
	}

	public Rectangle setHeight (float height) {
		return bounds.setHeight(height);
	}

	public Rectangle merge (float x, float y) {
		return bounds.merge(x, y);
	}

	public float getAspectRatio () {
		return bounds.getAspectRatio();
	}

	public Vector2 getSize (Vector2 size) {
		return bounds.getSize(size);
	}

	public float area () {
		return bounds.area();
	}

	public Rectangle merge (Rectangle rect) {
		return bounds.merge(rect);
	}

	public Rectangle setPosition (Vector2 position) {
		return bounds.setPosition(position);
	}

	public Rectangle setCenter (Vector2 position) {
		return bounds.setCenter(position);
	}

	public boolean contains (Rectangle rectangle) {
		return bounds.contains(rectangle);
	}

	public float getHeight () {
		return bounds.getHeight();
	}

	public float getX () {
		return bounds.getX();
	}

	public boolean overlaps (Rectangle r) {
		return bounds.overlaps(r);
	}

	public Rectangle setY (float y) {
		return bounds.setY(y);
	}

	public Rectangle setSize (float sizeXY) {
		return bounds.setSize(sizeXY);
	}

	public float getWidth () {
		return bounds.getWidth();
	}

	public boolean contains (Vector2 point) {
		return bounds.contains(point);
	}

	public Rectangle merge (Vector2 vec) {
		return bounds.merge(vec);
	}

	public Rectangle fitOutside (Rectangle rect) {
		return bounds.fitOutside(rect);
	}

	public Rectangle setX (float x) {
		return bounds.setX(x);
	}

	public Rectangle merge (Vector2[] vecs) {
		return bounds.merge(vecs);
	}

	public Rectangle set (Rectangle rect) {
		return bounds.set(rect);
	}

	public Rectangle setSize (float width, float height) {
		return bounds.setSize(width, height);
	}

	public float perimeter () {
		return bounds.perimeter();
	}

	public Vector2 getPosition (Vector2 position) {
		return bounds.getPosition(position);
	}

	public Rectangle setPosition (float x, float y) {
		return bounds.setPosition(x, y);
	}

	public Rectangle setCenter (float x, float y) {
		return bounds.setCenter(x, y);
	}

	public boolean contains (float x, float y) {
		return bounds.contains(x, y);
	}
}
