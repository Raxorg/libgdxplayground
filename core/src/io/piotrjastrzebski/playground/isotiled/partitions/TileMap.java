package io.piotrjastrzebski.playground.isotiled.partitions;

import com.badlogic.gdx.utils.Array;

/**
 * Created by EvilEntity on 07/01/2016.
 */
class TileMap {
	public final int mapWidth;
	public final int mapHeight;
	public final int regionSize;
	public final int regionsX;
	public final int regionsY;
	public final MapRegion[] regions;
	public final Tile[] tiles;

	public TileMap (int[] map, int mapWidth, int mapHeight, int regionSize) {
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		this.regionSize = regionSize;

		regionsX = mapWidth / regionSize;
		regionsY = mapHeight / regionSize;

		regions = new MapRegion[regionsX * regionsY];
		for (int x = 0; x < regionsX; x++) {
			for (int y = 0; y < regionsY; y++) {
				MapRegion region = new MapRegion(x + y * regionsX, x * regionSize, y * regionSize, regionSize);
				regions[region.id] = region;
			}
		}

		tiles = new Tile[mapWidth * mapHeight];
		for (int x = 0; x < TiledPartitionV2Test.MAP_WIDTH; x++) {
			for (int y = 0; y < TiledPartitionV2Test.MAP_HEIGHT; y++) {
				Tile tile = new Tile(x + y * TiledPartitionV2Test.MAP_WIDTH, x, y);
				// magic incantation to get correct id from the map above
				tile.setType(map[x + (TiledPartitionV2Test.MAP_HEIGHT - 1 - y) * TiledPartitionV2Test.MAP_WIDTH]);
				tiles[tile.id] = tile;
				addTileToRegion(tile);
			}
		}
	}

	public Tile getTileAt (int x, int y) {
		if (x < 0 || y < 0 || x >= mapWidth || y >= mapHeight)
			return null;
		return tiles[x + y * mapWidth];
	}

	public MapRegion getRegionAt (int x, int y) {
		int rx = x / regionSize;
		int ry = y / regionSize;
		if (rx < 0 || ry < 0 || rx >= regionsX || ry >= regionsY)
			return null;
		return regions[rx + ry * (TiledPartitionV2Test.MAP_WIDTH / TiledPartitionV2Test.REGION_SIZE)];
	}

	private void addTileToRegion (Tile tile) {
		MapRegion region = getRegionAt(tile.x, tile.y);
		if (region == null)
			throw new AssertionError("Region cant be null here!");
		region.addTile(tile);
	}

	public Tile getTile (int id) {
		return tiles[id];
	}
	private Array<MapRegion> rebuildQueue = new Array<>();
	/**
	 * Rebuild all regions
	 */
	public void rebuild () {
		rebuildQueue.clear();
		for (MapRegion region : regions) {
			rebuildQueue.add(region);
		}
		execRebuild();
	}

	private void execRebuild() {
		for (MapRegion region : rebuildQueue) {
			region.rebuild(this);
		}
		// note this is separate as it depends on surrounding regions being updated
		for (MapRegion region : rebuildQueue) {
			region.rebuildSubRegions(this);
		}
	}

	/**
	 * Rebuild region at x, y and surrounding
	 */
	public void rebuild (int x, int y) {
		rebuildQueue.clear();
		rebuildRegionAt(x, y);
		rebuildRegionAt(x -regionSize, y);
		rebuildRegionAt(x +regionSize, y);
		rebuildRegionAt(x, y -regionSize);
		rebuildRegionAt(x, y +regionSize);
		execRebuild();
	}

	private void rebuildRegionAt (int x, int y) {
		MapRegion region = getRegionAt(x, y);
		if (region != null) rebuildQueue.add(region);
	}
}
