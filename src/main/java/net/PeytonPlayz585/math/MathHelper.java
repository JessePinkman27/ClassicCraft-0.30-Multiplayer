package net.PeytonPlayz585.math;

public final class MathHelper {

   private static float[] SIN_TABLE = new float[4096];

   public static final float sin(float var0) {
	   return SIN_TABLE[(int)(var0 * 651.8986F) & 4095];
   }

   public static final float cos(float var0) {
	   return SIN_TABLE[(int)((var0 + ((float)Math.PI / 2F)) * 651.8986F) & 4095]; 
   }

   public static final float sqrt(float var0) {
      return (float)Math.sqrt((double)var0);
   }

   static {
	   
      for (int j = 0; j < 4096; ++j) {
         SIN_TABLE[j] = (float)Math.sin((double)(((float)j + 0.5F) / 4096.0F * ((float)Math.PI * 2F)));
      }

      for (int l = 0; l < 360; l += 90) {
         SIN_TABLE[(int)((float)l * 11.377778F) & 4095] = (float)Math.sin((double)((float)l * 0.017453292F));
      } 
   }
}