package net.io;

import java.util.ArrayList;
import java.util.Random;

import net.PeytonPlayz585.math.MathHelper;
import net.io.noise.*;

public class LevelUtils {

  private static int width;
  private static int height;
  private static int depth;
  private static Random random = new Random();
  private static byte[] blocks;
  private static int waterLevel;
  private static int[] h = new int[1048576];
  public static Level level;

  public LevelUtils() {

  }

  public static boolean levelExists() {
    return false;
  }

  public static void generateLevel() {
    int var2 = 128 << 1;
    int var3 = 128 << 1;
    System.out.println("Generating level");
    width = var2;
    depth = var3;
    height = 64;
    waterLevel = 32;
    blocks = new byte[var2 * var3 << 6];
    System.out.println("Raising...");

    LevelUtils var5 = new LevelUtils();
    CombinedNoise var6 = new CombinedNoise(new OctaveNoise(random, 8), new OctaveNoise(random, 8));
    CombinedNoise var7 = new CombinedNoise(new OctaveNoise(random, 8), new OctaveNoise(random, 8));
    OctaveNoise var8 = new OctaveNoise(random, 6);
    int[] var9 = new int[width * depth];
    float var10 = 1.3F;

    int var11;
    int var12;
    for (var11 = 0; var11 < var5.width; ++var11) {
      for (var12 = 0; var12 < var5.depth; ++var12) {
        double var13 = var6.compute((double)((float) var11 * var10), (double)((float) var12 * var10)) / 6.0D + (double) - 4;
        double var15 = var7.compute((double)((float) var11 * var10), (double)((float) var12 * var10)) / 5.0D + 10.0D + (double) - 4;
        if (var8.compute((double) var11, (double) var12) / 8.0D > 0.0D) {
          var15 = var13;
        }

        double var19;
        if ((var19 = Math.max(var13, var15) / 2.0D) < 0.0D) {
          var19 *= 0.8D;
        }

        var9[var11 + var12 * var5.width] = (int) var19;
      }
    }

    System.out.println("Eroding...");
    int[] var42 = var9;
    var5 = new LevelUtils();
    var7 = new CombinedNoise(new OctaveNoise(random, 8), new OctaveNoise(random, 8));
    CombinedNoise var49 = new CombinedNoise(new OctaveNoise(random, 8), new OctaveNoise(random, 8));

    int var23;
    int var51;
    int var54;
    for (var51 = 0; var51 < var5.width; ++var51) {
      for (var54 = 0; var54 < var5.depth; ++var54) {
        double var21 = var7.compute((double)(var51 << 1), (double)(var54 << 1)) / 8.0D;
        var12 = var49.compute((double)(var51 << 1), (double)(var54 << 1)) > 0.0D ? 1 : 0;
        if (var21 > 2.0D) {
          var23 = ((var42[var51 + var54 * var5.width] - var12) / 2 << 1) + var12;
          var42[var51 + var54 * var5.width] = var23;
        }
      }
    }

    System.out.println("Soiling...");
    var42 = var9;
    var5 = new LevelUtils();
    int var46 = width;
    int var48 = depth;
    var51 = height;
    OctaveNoise var53 = new OctaveNoise(random, 8);

    int var25;
    int var24;
    int var27;
    int var26;
    int var28;
    for (var24 = 0; var24 < var46; ++var24) {
      for (var11 = 0; var11 < var48; ++var11) {
        var12 = (int)(var53.compute((double) var24, (double) var11) / 24.0D) - 4;
        var25 = (var23 = var42[var24 + var11 * var46] + var5.waterLevel) + var12;
        var42[var24 + var11 * var46] = Math.max(var23, var25);
        if (var42[var24 + var11 * var46] > var51 - 2) {
          var42[var24 + var11 * var46] = var51 - 2;
        }

        if (var42[var24 + var11 * var46] < 1) {
          var42[var24 + var11 * var46] = 1;
        }

        for (var26 = 0; var26 < var51; ++var26) {
          var27 = (var26 * var5.depth + var11) * var5.width + var24;
          var28 = 0;
          if (var26 <= var23) {
            var28 = 3; //Block.DIRT
          }

          if (var26 <= var25) {
            var28 = 1; //Block.STONE
          }

          if (var26 == 0) {
            var28 = 10; //Block.LAVA
          }

          var5.blocks[var27] = (byte) var28;
        }
      }
    }

    System.out.println("Carving...");
    boolean var45 = true;
    boolean var44 = false;
    var5 = new LevelUtils();
    var48 = width;
    var51 = depth;
    var54 = height;
    var24 = var48 * var51 * var54 / 256 / 64 << 1;

    for (var11 = 0; var11 < var24; ++var11) {
      float var55 = var5.random.nextFloat() * (float) var48;
      float var59 = var5.random.nextFloat() * (float) var54;
      float var56 = var5.random.nextFloat() * (float) var51;
      var26 = (int)((var5.random.nextFloat() + var5.random.nextFloat()) * 200.0F);
      float var61 = var5.random.nextFloat() * 3.1415927F * 2.0F;
      float var64 = 0.0F;
      float var29 = var5.random.nextFloat() * 3.1415927F * 2.0F;
      float var30 = 0.0F;
      float var31 = var5.random.nextFloat() * var5.random.nextFloat();

      for (int var32 = 0; var32 < var26; ++var32) {
        var55 += MathHelper.sin(var61) * MathHelper.cos(var29);
        var56 += MathHelper.cos(var61) * MathHelper.cos(var29);
        var59 += MathHelper.sin(var29);
        var61 += var64 * 0.2F;
        var64 = (var64 *= 0.9F) + (var5.random.nextFloat() - var5.random.nextFloat());
        var29 = (var29 + var30 * 0.5F) * 0.5F;
        var30 = (var30 *= 0.75F) + (var5.random.nextFloat() - var5.random.nextFloat());
        if (var5.random.nextFloat() >= 0.25F) {
          float var43 = var55 + (var5.random.nextFloat() * 4.0F - 2.0F) * 0.2F;
          float var50 = var59 + (var5.random.nextFloat() * 4.0F - 2.0F) * 0.2F;
          float var33 = var56 + (var5.random.nextFloat() * 4.0F - 2.0F) * 0.2F;
          float var34 = ((float) var5.height - var50) / (float) var5.height;
          var34 = 1.2F + (var34 * 3.5F + 1.0F) * var31;
          var34 = MathHelper.sin((float) var32 * 3.1415927F / (float) var26) * var34;

          for (int var35 = (int)(var43 - var34); var35 <= (int)(var43 + var34); ++var35) {
            for (int var36 = (int)(var50 - var34); var36 <= (int)(var50 + var34); ++var36) {
              for (int var37 = (int)(var33 - var34); var37 <= (int)(var33 + var34); ++var37) {
                float var38 = (float) var35 - var43;
                float var39 = (float) var36 - var50;
                float var40 = (float) var37 - var33;
                if (var38 * var38 + var39 * var39 * 2.0F + var40 * var40 < var34 * var34 && var35 >= 1 && var36 >= 1 && var37 >= 1 && var35 < var5.width - 1 && var36 < var5.height - 1 && var37 < var5.depth - 1) {
                  int var66 = (var36 * var5.depth + var37) * var5.width + var35;
                  if (var5.blocks[var66] == 1 /* Block.STONE */ ) {
                    var5.blocks[var66] = 0;
                  }
                }
              }
            }
          }
        }
      }
    }

    populateOre(16, 90, 1, 4); //Block.COAL_ORE
    populateOre(15, 70, 2, 4); //Block.IRON_ORE
    populateOre(14, 50, 3, 4); //Block.GOLD_ORE
    System.out.println("Watering...");
    var5 = new LevelUtils();
    var51 = 9; //Block.STATIONARY_WATER

    for (var54 = 0; var54 < var5.width; ++var54) {
      var5.flood(var54, var5.height / 2 - 1, 0, 0, var51);
      var5.flood(var54, var5.height / 2 - 1, var5.depth - 1, 0, var51);
    }

    for (var54 = 0; var54 < var5.depth; ++var54) {
      var5.flood(0, var5.height / 2 - 1, var54, 0, var51);
      var5.flood(var5.width - 1, var5.height / 2 - 1, var54, 0, var51);
    }

    var54 = var5.width * var5.depth / 8000;

    for (var24 = 0; var24 < var54; ++var24) {
      var11 = var5.random.nextInt(var5.width);
      var12 = var5.waterLevel - 1 - var5.random.nextInt(2);
      var23 = var5.random.nextInt(var5.depth);
      if (var5.blocks[(var12 * var5.depth + var23) * var5.width + var11] == 0) {
        var5.flood(var11, var12, var23, 0, var51);
      }
    }

    System.out.println("Melting...");
    var5 = new LevelUtils();
    var46 = width * depth * height / 20000;

    for (var48 = 0; var48 < var46; ++var48) {
      var51 = var5.random.nextInt(var5.width);
      var54 = (int)(var5.random.nextFloat() * var5.random.nextFloat() * (float)(var5.waterLevel - 3));
      var24 = var5.random.nextInt(var5.depth);
      if (var5.blocks[(var54 * var5.depth + var24) * var5.width + var51] == 0) {
        var5.flood(var51, var54, var24, 0, 11 /* Block.STATIONARY_LAVA */ );
      }
    }

    System.out.println("Growing...");
    var42 = var9;
    var5 = new LevelUtils();
    var46 = width;
    var48 = depth;
    var51 = height;
    var53 = new OctaveNoise(random, 8);
    OctaveNoise var58 = new OctaveNoise(random, 8);

    int var63;
    for (var11 = 0; var11 < var46; ++var11) {
      for (var12 = 0; var12 < var48; ++var12) {
        boolean var60 = var53.compute((double) var11, (double) var12) > 8.0D;
        boolean var57 = var58.compute((double) var11, (double) var12) > 12.0D;
        var27 = ((var26 = var42[var11 + var12 * var46]) * var5.depth + var12) * var5.width + var11;
        if (((var28 = var5.blocks[((var26 + 1) * var5.depth + var12) * var5.width + var11] & 255) == 8 /* Block.WATER */ || var28 == 9 /* Block.STATIONARY_WATER */ ) && var26 <= var51 / 2 - 1 && var57) {
          var5.blocks[var27] = (byte) 13; //Block.GRAVEL
        }

        if (var28 == 0) {
          var63 = 2; //Block.GRASS
          if (var26 <= var51 / 2 - 1 && var60) {
            var63 = 12; //Block.SAND
          }

          var5.blocks[var27] = (byte) var63;
        }
      }
    }

    System.out.println("Planting...");
    var42 = var9;
    var5 = new LevelUtils();
    var46 = width;
    var48 = width * depth / 3000;

    for (var51 = 0; var51 < var48; ++var51) {
      var54 = var5.random.nextInt(2);
      var24 = var5.random.nextInt(var5.width);
      var11 = var5.random.nextInt(var5.depth);

      for (var12 = 0; var12 < 10; ++var12) {
        var23 = var24;
        var25 = var11;

        for (var26 = 0; var26 < 5; ++var26) {
          var23 += var5.random.nextInt(6) - var5.random.nextInt(6);
          var25 += var5.random.nextInt(6) - var5.random.nextInt(6);
          if ((var54 < 2 || var5.random.nextInt(4) == 0) && var23 >= 0 && var25 >= 0 && var23 < var5.width && var25 < var5.depth) {
            var27 = var42[var23 + var25 * var46] + 1;
            if ((var5.blocks[(var27 * var5.depth + var25) * var5.width + var23] & 255) == 0) {
              var63 = (var27 * var5.depth + var25) * var5.width + var23;
              if ((var5.blocks[((var27 - 1) * var5.depth + var25) * var5.width + var23] & 255) == 2 /* Block.GRASS */ ) {
                if (var54 == 0) {
                  var5.blocks[var63] = (byte) 37; //Block.DANDELION
                } else if (var54 == 1) {
                  var5.blocks[var63] = (byte) 38; //Block.ROSE
                }
              }
            }
          }
        }
      }
    }

    var42 = var9;
    var5 = new LevelUtils();
    var46 = width;
    var51 = width * depth * height / 2000;

    for (var54 = 0; var54 < var51; ++var54) {
      var24 = var5.random.nextInt(2);
      var11 = var5.random.nextInt(var5.width);
      var12 = var5.random.nextInt(var5.height);
      var23 = var5.random.nextInt(var5.depth);

      for (var25 = 0; var25 < 20; ++var25) {
        var26 = var11;
        var27 = var12;
        var28 = var23;

        for (var63 = 0; var63 < 5; ++var63) {
          var26 += var5.random.nextInt(6) - var5.random.nextInt(6);
          var27 += var5.random.nextInt(2) - var5.random.nextInt(2);
          var28 += var5.random.nextInt(6) - var5.random.nextInt(6);
          if ((var24 < 2 || var5.random.nextInt(4) == 0) && var26 >= 0 && var28 >= 0 && var27 >= 1 && var26 < var5.width && var28 < var5.depth && var27 < var42[var26 + var28 * var46] - 1 && (var5.blocks[(var27 * var5.depth + var28) * var5.width + var26] & 255) == 0) {
            int var62 = (var27 * var5.depth + var28) * var5.width + var26;
            if ((var5.blocks[((var27 - 1) * var5.depth + var28) * var5.width + var26] & 255) == 1 /* Block.STONE */ ) {
              if (var24 == 0) {
                var5.blocks[var62] = (byte) 39; //Block.BROWN_MUSHROOM
              } else if (var24 == 1) {
                var5.blocks[var62] = (byte) 40; //Block.RED_MUSHROOM
              }
            }
          }
        }
      }
    }

    Level var65;
    (var65 = new Level()).waterLevel = waterLevel;
    var65.setData(var2, 64, var3, blocks);
    int[] var52 = var9;
    Level var47 = var65;
    var5 = new LevelUtils();
    var48 = width;
    var51 = width * depth / 4000;

    for (var54 = 0; var54 < var51; ++var54) {
      var24 = var5.random.nextInt(var5.width);
      var11 = var5.random.nextInt(var5.depth);

      for (var12 = 0; var12 < 20; ++var12) {
        var23 = var24;
        var25 = var11;

        for (var26 = 0; var26 < 20; ++var26) {
          var23 += var5.random.nextInt(6) - var5.random.nextInt(6);
          var25 += var5.random.nextInt(6) - var5.random.nextInt(6);
          if (var23 >= 0 && var25 >= 0 && var23 < var5.width && var25 < var5.depth) {
            var27 = var52[var23 + var25 * var48] + 1;
            if (var5.random.nextInt(4) == 0) {
              var47.maybeGrowTree(var23, var27, var25);
            }
          }
        }
      }
    }

    level = var65;
    System.out.println("Done!");
    System.out.println("WebSocket server started on port 25565!");
  }

  private static void populateOre(int var1, int var2, int var3, int var4) {
    byte var25 = (byte) var1;
    var4 = width;
    int var5 = depth;
    int var6 = height;
    int var7 = var4 * var5 * var6 / 256 / 64 * var2 / 100;

    for (int var8 = 0; var8 < var7; ++var8) {
      float var9 = random.nextFloat() * (float) var4;
      float var10 = random.nextFloat() * (float) var6;
      float var11 = random.nextFloat() * (float) var5;
      int var12 = (int)((random.nextFloat() + random.nextFloat()) * 75.0F * (float) var2 / 100.0F);
      float var13 = random.nextFloat() * 3.1415927F * 2.0F;
      float var14 = 0.0F;
      float var15 = random.nextFloat() * 3.1415927F * 2.0F;
      float var16 = 0.0F;

      for (int var17 = 0; var17 < var12; ++var17) {
        var9 += MathHelper.sin(var13) * MathHelper.cos(var15);
        var11 += MathHelper.cos(var13) * MathHelper.cos(var15);
        var10 += MathHelper.sin(var15);
        var13 += var14 * 0.2F;
        var14 = (var14 *= 0.9F) + (random.nextFloat() - random.nextFloat());
        var15 = (var15 + var16 * 0.5F) * 0.5F;
        var16 = (var16 *= 0.9F) + (random.nextFloat() - random.nextFloat());
        float var18 = MathHelper.sin((float) var17 * 3.1415927F / (float) var12) * (float) var2 / 100.0F + 1.0F;

        for (int var19 = (int)(var9 - var18); var19 <= (int)(var9 + var18); ++var19) {
          for (int var20 = (int)(var10 - var18); var20 <= (int)(var10 + var18); ++var20) {
            for (int var21 = (int)(var11 - var18); var21 <= (int)(var11 + var18); ++var21) {
              float var22 = (float) var19 - var9;
              float var23 = (float) var20 - var10;
              float var24 = (float) var21 - var11;
              if (var22 * var22 + var23 * var23 * 2.0F + var24 * var24 < var18 * var18 && var19 >= 1 && var20 >= 1 && var21 >= 1 && var19 < width - 1 && var20 < height - 1 && var21 < depth - 1) {
                int var26 = (var20 * depth + var21) * width + var19;
                if (blocks[var26] == 1 /* Block.STONE */ ) {
                  blocks[var26] = var25;
                }
              }
            }
          }
        }
      }
    }

  }

  private static long flood(int var1, int var2, int var3, int var4, int var5) {
    byte var20 = (byte) var5;
    ArrayList var21 = new ArrayList();
    byte var6 = 0;
    int var7 = 1;

    int var8;
    for (var8 = 1; 1 << var7 < width; ++var7) {
      ;
    }

    while (1 << var8 < depth) {
      ++var8;
    }

    int var9 = depth - 1;
    int var10 = width - 1;
    int var22 = var6 + 1;
    h[0] = ((var2 << var8) + var3 << var7) + var1;
    long var11 = 0L;
    var1 = width * depth;

    while (var22 > 0) {
      --var22;
      var2 = h[var22];
      if (var22 == 0 && var21.size() > 0) {
        h = (int[]) var21.remove(var21.size() - 1);
        var22 = h.length;
      }

      var3 = var2 >> var7 & var9;
      int var13 = var2 >> var7 + var8;

      int var14;
      int var15;
      for (var15 = var14 = var2 & var10; var14 > 0 && blocks[var2 - 1] == 0; --var2) {
        --var14;
      }

      while (var15 < width && blocks[var2 + var15 - var14] == 0) {
        ++var15;
      }

      int var16 = var2 >> var7 & var9;
      int var17 = var2 >> var7 + var8;
      if (var16 != var3 || var17 != var13) {
        System.err.println("Diagonal flood!?");
      }

      boolean var23 = false;
      boolean var24 = false;
      boolean var18 = false;
      var11 += (long)(var15 - var14);

      for (var14 = var14; var14 < var15; ++var14) {
        blocks[var2] = var20;
        boolean var19;
        if (var3 > 0) {
          if ((var19 = blocks[var2 - width] == 0) && !var23) {
            if (var22 == h.length) {
              var21.add(h);
              h = new int[1048576];
              var22 = 0;
            }

            h[var22++] = var2 - width;
          }

          var23 = var19;
        }

        if (var3 < depth - 1) {
          if ((var19 = blocks[var2 + width] == 0) && !var24) {
            if (var22 == h.length) {
              var21.add(h);
              h = new int[1048576];
              var22 = 0;
            }

            h[var22++] = var2 + width;
          }

          var24 = var19;
        }

        if (var13 > 0) {
          byte var25 = blocks[var2 - var1];
          if ((var20 == 10 /* Block.LAVA */ || var20 == 11 /* Block.STATIONARY_LAVA */ ) && (var25 == 8 /* Block.WATER */ || var25 == 9 /* Block.STATIONARY_WATER */ )) {
            blocks[var2 - var1] = (byte) 1; /* Block.STONE */
          }

          if ((var19 = var25 == 0) && !var18) {
            if (var22 == h.length) {
              var21.add(h);
              h = new int[1048576];
              var22 = 0;
            }

            h[var22++] = var2 - var1;
          }

          var18 = var19;
        }

        ++var2;
      }
    }

    return var11;
  }

}