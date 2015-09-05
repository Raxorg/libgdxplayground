package io.piotrjastrzebski.playground.dungeon;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ShortArray;
import io.piotrjastrzebski.playground.BaseScreen;
import io.piotrjastrzebski.playground.GameReset;

import java.util.Comparator;

/**
 * Created by PiotrJ on 02/09/15.
 */
public class DungeonGeneratorTest extends BaseScreen {
	private int roomID;
	Array<Room> rooms = new Array<>();

	World b2d;
	Box2DDebugRenderer b2dd;
	Vector2 tmp = new Vector2();
	Grid grid;
	GenSettings settings;

	boolean drawBodies;
	public DungeonGeneratorTest (GameReset game) {
		super(game);
		b2d = new World(new Vector2(), true);
		b2dd = new Box2DDebugRenderer();
		settings = new GenSettings()
			.setGridSize(.25f)
			.setSpawnWidth(20).setSpawnHeight(10)
			.setRoomWidth(4).setRoomHeight(4)
			.setMainRoomScale(1.3f)
			.setReconnectChance(.2f)
			.setHallwaysWidth(3);

		grid = new Grid();
		restart();
	}

	private void restart () {
		graph.clear();
		mainRooms.clear();
		if (rooms.size > 0) {
			for (Room room : rooms) {
				b2d.destroyBody(room.body);
			}
		}
		rooms.clear();
		paths.clear();

		float gridSize = settings.getGridSize();
		grid.setSize(gridSize);
		float roomWidth = settings.getRoomWidth();
		float roomHeight = settings.getRoomHeight();
		float spawnWidth = settings.getSpawnWidth();
		float spawnHeight = settings.getSpawnHeight();
		for (int i = 0; i < 100; i++) {
			Room room = new Room(roomID++, gridSize);
			float w = Utils.roundedRngFloat(roomWidth, gridSize);
			if (w < 0) w = -w;
			float h = Utils.roundedRngFloat(roomHeight, gridSize);
			if (h < 0) h = -h;
			if (w < gridSize || h < gridSize) continue;
			Utils.roundedPointInEllipse(spawnWidth, spawnHeight, gridSize, tmp);
			room.set(tmp.x, tmp.y, w, h);
			createBody(room);
			rooms.add(room);
		}
	}

	private void createBody (Room room) {
		Rectangle b = room.bounds;
		if (b.width < 0.1f || b.height < 0.1f) {
			return;
		}
		BodyDef bodyDef = new BodyDef();
		bodyDef.fixedRotation = true;
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(b.x, b.y);
		Body body = b2d.createBody(bodyDef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(b.width / 2, b.height / 2);

		FixtureDef fd = new FixtureDef();
		fd.restitution = 0;
		fd.friction = 0.5f;
		fd.density = 1;
		fd.shape = shape;

		body.createFixture(fd);

		shape.dispose();

		room.body = body;
	}

	int pIters = 1000;
	@Override public void render (float delta) {
		super.render(delta);
		boolean settled = true;
		for (int i = 0; i < pIters; i++) {
			b2d.step(0.1f, 12, 8);
			for (Room room : rooms) {
				if (!room.isSleeping()) {
					settled = false;
					break;
				}
			}
			if (settled) break;
		}

		if (drawBodies){
			b2dd.render(b2d, gameCamera.combined);
		}
		Gdx.gl.glEnable(GL20.GL_BLEND);
		renderer.setProjectionMatrix(gameCamera.combined);
		renderer.begin(ShapeRenderer.ShapeType.Line);

		grid.draw(renderer);
		for (Room room : rooms) {
			room.update();
			room.draw(renderer);
		}
		if (settled && mainRooms.size == 0) {
			float mw = settings.getRoomWidth() * settings.getMainRoomScale();
			float mh = settings.getRoomHeight() * settings.getMainRoomScale();
			for (Room room : rooms) {
				if (room.bounds.width >= mw && room.bounds.height >= mh) {
					room.isMain = true;
					mainRooms.add(room);
				}
			}
			// sort so main rooms are drawn lsat
			rooms.sort(new Comparator<Room>() {
				@Override public int compare (Room o1, Room o2) {
					int main = Boolean.compare(o1.isMain, o2.isMain);
					if (main != 0) {
						return main;
					}
					return Boolean.compare(o1.isHallway, o2.isHallway);
				}
			});
			triangulate();
		}
		graph.render(renderer);
		renderer.setColor(Color.BLUE);
		for (HallwayPath path : paths) {
			path.draw(renderer);
		}
		renderer.end();
	}

	private static Polygon poly = new Polygon();
	private static float[] verts = new float[8];

	Array<Room> mainRooms = new Array<>();
	RoomGraph graph = new RoomGraph();
	private void triangulate () {
		DelaunayTriangulator triangulator = new DelaunayTriangulator();

		float[] points = new float[mainRooms.size * 2];
		for (int i = 0; i < points.length; i+=2) {
			Room room = mainRooms.get(i / 2);
			points[i] = room.cx();
			points[i+1] = room.cy();
		}

		ShortArray indicies = triangulator.computeTriangles(points, 0, points.length, false);

		graph.clear();

		for (int i = 0; i < indicies.size; i += 3) {
			int p1 = indicies.get(i) * 2;
			int p2 = indicies.get(i + 1) * 2;
			int p3 = indicies.get(i + 2) * 2;
			// this is pretty dumb...
			Room roomA = getRoom(points[p1], points[p1 + 1]);
			Room roomB = getRoom(points[p2], points[p2 + 1]);
			Room roomC = getRoom(points[p3], points[p3 + 1]);
			graph.add(roomA, roomB);
			graph.add(roomA, roomC);
			graph.add(roomB, roomC);
		}

		createMST();
	}

	private void createMST () {
		/*
		kruskal's algorithm, we dont need anything fancy
		 */
		Array<RoomEdge> edges = graph.getEdges();
		edges.sort(new Comparator<RoomEdge>() {
			@Override public int compare (RoomEdge o1, RoomEdge o2) {
				return Float.compare(o1.len, o2.len);
			}
		});
		RoomGraph mstGraph = new RoomGraph();
		for (RoomEdge edge : edges) {
			if (!mstGraph.isConnected(edge)) {
				mstGraph.add(edge);
				edge.mst = true;
			}
		}

		for (RoomEdge edge : edges) {
			if (!edge.mst && MathUtils.random() < settings.getReconnectChance()) {
				edge.mst = true;
				edge.recon = true;
			}
		}

		createHallways();
	}

	Array<HallwayPath> paths = new Array<>();
	private void createHallways () {
		Array<RoomEdge> edges = graph.getEdges();
		for (RoomEdge e : edges) {
			if (!e.mst)
				continue;
			HallwayPath path = new HallwayPath();
			Rectangle bA = e.roomA.bounds;
			Rectangle bB = e.roomB.bounds;
			float min, max, mid;
			// rooms cant overlap on both axis
			if (bA.x < bB.x + bB.width && bA.x + bA.width > bB.x) {
				// no need for 0 len hallway
				if (MathUtils.isEqual(bA.y, bB.y + bB.height) || MathUtils.isEqual(bA.y + bA.height, bB.y)) {
					continue;
				}
				min = (bA.x < bB.x)?bB.x:bA.x;
				max = (bA.x + bA.width < bB.x + bB.width)?bA.x + bA.width:bB.x + bB.width;

				mid = (min + max) /2;
//				mid = Utils.roundToSize(mid, settings.getGridSize());

				if (bA.y > bB.y) {
					path.set(mid, bA.y, mid, bB.y + bB.height);
				} else {
					path.set(mid, bA.y + bA.height, mid, bB.y);
				}
			} else if (bA.y < bB.y + bB.height && bA.y + bA.height > bB.y) {
				// no need for 0 len hallway
				if (MathUtils.isEqual(bA.x, bB.x + bB.width) || MathUtils.isEqual(bA.x + bA.width, bB.x)) {
					continue;
				}

				min = (bA.y < bB.y)?bB.y:bA.y;
				max = (bA.y + bA.height < bB.y + bB.height)?bA.y + bA.height:bB.y + bB.height;

				mid = (min + max) /2;
//				mid = Utils.roundToSize(mid, settings.getGridSize());

				if (bA.x > bB.x) {
					path.set(bA.x, mid, bB.x + bB.width, mid);
				} else {
					path.set(bA.x + bA.width, mid, bB.x, mid);
				}
			} else {
				// need to make a L shaped hallway
				float ax = bA.x;
				float ay = bA.y;
				float aw = bA.width;
				float ah = bA.height;
				float bx = bB.x;
				float by = bB.y;
				float bw = bB.width;
				float bh = bB.height;
				float mx, my;
				// pack a side
				// can we make this simpler? im dumb
				if (MathUtils.randomBoolean()) {
					mx = ax + aw / 2;
					my = by + bh / 2;
					if (ax < bx) {
						if (ay < by) {
							path.set(mx, ay + ah, mx, my, bx, my);
						} else {
							path.set(mx, ay, mx, my, bx, my);
						}
					} else {
						if (ay > by) {
							path.set(mx, ay, mx, my, bx + bw, my);
						} else {
							path.set(mx, ay + ah, mx, my, bx + bw, my);
						}
					}
				} else {
					mx = bx + bw / 2;
					my = ay + ah / 2;
					if (ax < bx) {
						if (ay < by) {
							path.set(ax + aw, my, mx, my, mx, by);
						} else {
							path.set(ax + aw, my, mx, my, mx, by + bh);
						}
					} else {
						if (ay > by) {
							path.set(ax, my, mx, my, mx, by + bh);
						} else {
							path.set(ax, my, mx, my, mx, by);
						}
					}
				}
			}
			paths.add(path);
		}

		addHallwayRooms();
	}

	private void addHallwayRooms () {
		for (HallwayPath path : paths) {
			for (Room room : rooms) {
				if (room.isMain) continue;
				if (path.intersects(room)) {
					room.isHallway = true;
				}
			}
		}
	}

	private Room getRoom (float cx, float cy) {
		for (Room room : mainRooms) {
			if (MathUtils.isEqual(cx, room.cx()) && MathUtils.isEqual(cy, room.cy())) {
				return room;
			}
		}
		return null;
	}

	@Override public void dispose () {
		super.dispose();
		b2d.dispose();
	}

	@Override public void resize (int width, int height) {
		super.resize(width, height);
		if (grid != null)
			grid.setViewPort(width, height);
	}

	@Override public boolean keyDown (int keycode) {
		switch (keycode) {
		case Input.Keys.SPACE:
			restart();
			break;
		case Input.Keys.B:
			drawBodies = !drawBodies;
			break;
		case Input.Keys.Q:
			if (pIters == 1) {
				pIters = 1000;
			} else {
				pIters = 1;
			}
			break;
		}
		return super.keyDown(keycode);
	}

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;
		config.useHDPI = true;
		new LwjglApplication(new Game() {
			@Override public void create () {
				setScreen(new DungeonGeneratorTest(new Reset()));
			}
		}, config);
	}

	public static class Reset implements GameReset {
		@Override public void reset () {
			Gdx.app.exit();
		}
	}
}