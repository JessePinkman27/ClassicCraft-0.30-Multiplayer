package net.io;

import java.util.*;

public class Level {

  public int width;
  public int height;
  public int depth;
  public byte[] blocks;
  public int xSpawn;
  public int ySpawn;
  public int zSpawn;
  public float rotSpawn;
  public int waterLevel;
  public Random random = new Random();

  public void setData(int var1, int var2, int var3, byte[] var4) {
    this.width = var1;
    this.height = var3;
    this.depth = var2;
    this.blocks = var4;

    this.findSpawn();
    System.gc();
  }

  public void findSpawn() {
    Random var1 = new Random();
    int var2 = 0;

    int var3;
    int var4;
    int var5;
    do {
      ++var2;
      var3 = var1.nextInt(this.width / 2) + this.width / 4;
      var4 = var1.nextInt(this.height / 2) + this.height / 4;
      var5 = var1.nextInt(this.depth / 2) + this.depth / 4;
      if (var2 == 10000) {
        this.xSpawn = var3;
        this.ySpawn = -100;
        this.zSpawn = var4;
        return;
      }
    } while ((float) var5 <= this.getWaterLevel());

    this.xSpawn = var3;
    this.ySpawn = var5;
    this.zSpawn = var4;
  }

  public float getGroundLevel() {
    return this.getWaterLevel() - 2.0F;
  }

  public float getWaterLevel() {
    return (float) this.waterLevel;
  }

  public boolean maybeGrowTree(int var1, int var2, int var3) {
    int var4 = random.nextInt(3) + 4;
    boolean var5 = true;

    int var6;
    int var8;
    int var9;
    for (var6 = var2; var6 <= var2 + 1 + var4; ++var6) {
      byte var7 = 1;
      if (var6 == var2) {
        var7 = 0;
      }

      if (var6 >= var2 + 1 + var4 - 2) {
        var7 = 2;
      }

      for (var8 = var1 - var7; var8 <= var1 + var7 && var5; ++var8) {
        for (var9 = var3 - var7; var9 <= var3 + var7 && var5; ++var9) {
          if (var8 >= 0 && var6 >= 0 && var9 >= 0 && var8 < this.width && var6 < this.depth && var9 < this.height) {
            if ((this.blocks[(var6 * this.height + var9) * this.width + var8] & 255) != 0) {
              var5 = false;
            }
          } else {
            var5 = false;
          }
        }
      }
    }

    if (!var5) {
      return false;
    } else if ((this.blocks[((var2 - 1) * this.height + var3) * this.width + var1] & 255) == 2 /* Block.GRASS */ && var2 < this.depth - var4 - 1) {
      this.setTile(var1, var2 - 1, var3, 3 /* Block.DIRT */ );

      int var13;
      for (var13 = var2 - 3 + var4; var13 <= var2 + var4; ++var13) {
        var8 = var13 - (var2 + var4);
        var9 = 1 - var8 / 2;

        for (int var10 = var1 - var9; var10 <= var1 + var9; ++var10) {
          int var12 = var10 - var1;

          for (var6 = var3 - var9; var6 <= var3 + var9; ++var6) {
            int var11 = var6 - var3;
            if (Math.abs(var12) != var9 || Math.abs(var11) != var9 || random.nextInt(2) != 0 && var8 != 0) {
              this.setTile(var10, var13, var6, 18 /* Block.LEAVES */ );
            }
          }
        }
      }

      for (var13 = 0; var13 < var4; ++var13) {
        this.setTile(var1, var2 + var13, var3, 17 /* Block.LOG */ );
      }

      return true;
    } else {
      return false;
    }
  }

  public boolean setTile(int var1, int var2, int var3, int var4) {
    if (this.setTileNoNeighborChange(var1, var2, var3, var4)) {
      return true;
    } else {
      return false;
    }
  }

  public boolean setTileNoNeighborChange(int var1, int var2, int var3, int var4) {
    return this.netSetTileNoNeighborChange(var1, var2, var3, var4);
  }

  public boolean netSetTileNoNeighborChange(int var1, int var2, int var3, int var4) {
    if (var1 >= 0 && var2 >= 0 && var3 >= 0 && var1 < this.width && var2 < this.depth && var3 < this.height) {
      if (var4 == this.blocks[(var2 * this.height + var3) * this.width + var1]) {
        return false;
      } else {
        if (var4 == 0 && (var1 == 0 || var3 == 0 || var1 == this.width - 1 || var3 == this.height - 1) && (float) var2 >= this.getGroundLevel() && (float) var2 < this.getWaterLevel()) {
          var4 = 8; /* Block.WATER */
        }

        this.blocks[(var2 * this.height + var3) * this.width + var1] = (byte) var4;

        return true;
      }
    } else {
      return false;
    }
  }

}