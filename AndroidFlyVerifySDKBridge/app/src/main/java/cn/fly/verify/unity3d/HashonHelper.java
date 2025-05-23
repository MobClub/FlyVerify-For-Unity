package cn.fly.verify.unity3d;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HashonHelper {
    public static <T> HashMap<String, T> fromJson(String var0) {
        if (var0 != null && !var0.isEmpty()) {
            try {
                if (var0.startsWith("[") && var0.endsWith("]")) {
                    var0 = "{\"fakelist\":" + var0 + "}";
                }

                JSONObject var1 = new JSONObject(var0);
                return a(var1);
            } catch (Throwable var2) {
                return new HashMap();
            }
        } else {
            return new HashMap();
        }
    }

    public static <T> String fromHashMap(HashMap<String, T> var0) {
        try {
            JSONObject var1 = a(var0);
            return var1 == null ? "" : var1.toString();
        } catch (Throwable var2) {
            return "";
        }
    }

    private static <T> JSONObject a(HashMap<String, T> var0) throws Throwable {
        JSONObject var1 = new JSONObject();

        Map.Entry var3;
        Object var4;
        for(Iterator var2 = var0.entrySet().iterator(); var2.hasNext(); var1.put((String)var3.getKey(), var4)) {
            var3 = (Map.Entry)var2.next();
            var4 = var3.getValue();
            if (var4 instanceof HashMap) {
                var4 = a((HashMap)var4);
            } else if (var4 instanceof ArrayList) {
                var4 = a((ArrayList)var4);
            } else if (a(var4)) {
                var4 = a(b(var4));
            }
        }

        return var1;
    }

    private static ArrayList<?> b(Object var0) {
        ArrayList var1;
        int var3;
        int var4;
        if (var0 instanceof byte[]) {
            var1 = new ArrayList();
            byte[] var13 = (byte[])var0;
            var3 = var13.length;

            for(var4 = 0; var4 < var3; ++var4) {
                byte var19 = var13[var4];
                var1.add(var19);
            }

            return var1;
        } else if (var0 instanceof short[]) {
            var1 = new ArrayList();
            short[] var12 = (short[])var0;
            var3 = var12.length;

            for(var4 = 0; var4 < var3; ++var4) {
                short var18 = var12[var4];
                var1.add(var18);
            }

            return var1;
        } else {
            int var14;
            if (var0 instanceof int[]) {
                var1 = new ArrayList();
                int[] var11 = (int[])var0;
                var3 = var11.length;

                for(var4 = 0; var4 < var3; ++var4) {
                    var14 = var11[var4];
                    var1.add(var14);
                }

                return var1;
            } else if (var0 instanceof long[]) {
                var1 = new ArrayList();
                long[] var10 = (long[])var0;
                var3 = var10.length;

                for(var4 = 0; var4 < var3; ++var4) {
                    long var17 = var10[var4];
                    var1.add(var17);
                }

                return var1;
            } else if (var0 instanceof float[]) {
                var1 = new ArrayList();
                float[] var9 = (float[])var0;
                var3 = var9.length;

                for(var4 = 0; var4 < var3; ++var4) {
                    float var16 = var9[var4];
                    var1.add(var16);
                }

                return var1;
            } else if (var0 instanceof double[]) {
                var1 = new ArrayList();
                double[] var8 = (double[])var0;
                var3 = var8.length;

                for(var4 = 0; var4 < var3; ++var4) {
                    double var15 = var8[var4];
                    var1.add(var15);
                }

                return var1;
            } else if (var0 instanceof char[]) {
                var1 = new ArrayList();
                char[] var7 = (char[])var0;
                var3 = var7.length;

                for(var4 = 0; var4 < var3; ++var4) {
                    var14 = var7[var4];
                    var1.add(Character.valueOf((char)var14));
                }

                return var1;
            } else if (!(var0 instanceof boolean[])) {
                return var0 instanceof String[] ? new ArrayList(Arrays.asList((String[])var0)) : null;
            } else {
                var1 = new ArrayList();
                boolean[] var2 = (boolean[])var0;
                var3 = var2.length;

                for(var4 = 0; var4 < var3; ++var4) {
                    boolean var5 = var2[var4];
                    var1.add(var5);
                }

                return var1;
            }
        }
    }

    private static boolean a(Object var0) {
        return var0 instanceof byte[] || var0 instanceof short[] || var0 instanceof int[] || var0 instanceof long[] || var0 instanceof float[] || var0 instanceof double[] || var0 instanceof char[] || var0 instanceof boolean[] || var0 instanceof String[];
    }

    private static JSONArray a(ArrayList<Object> var0) throws Throwable {
        JSONArray var1 = new JSONArray();

        Object var3;
        for(Iterator var2 = var0.iterator(); var2.hasNext(); var1.put(var3)) {
            var3 = var2.next();
            if (var3 instanceof HashMap) {
                var3 = a((HashMap)var3);
            } else if (var3 instanceof ArrayList) {
                var3 = a((ArrayList)var3);
            }
        }

        return var1;
    }

    private static <T> HashMap<String, T> a(JSONObject var0) throws Throwable {
        HashMap var1 = new HashMap();
        Iterator var2 = var0.keys();

        while(var2.hasNext()) {
            String var3 = (String)var2.next();
            Object var4 = var0.opt(var3);
            if (JSONObject.NULL.equals(var4)) {
                var4 = null;
            }

            if (var4 != null) {
                if (var4 instanceof JSONObject) {
                    var4 = a((JSONObject)var4);
                } else if (var4 instanceof JSONArray) {
                    var4 = a((JSONArray)var4);
                }

                var1.put(var3, var4);
            }
        }

        return var1;
    }
    private static ArrayList<Object> a(JSONArray var0) throws Throwable {
        ArrayList var1 = new ArrayList();
        int var2 = 0;

        for(int var3 = var0.length(); var2 < var3; ++var2) {
            Object var4 = var0.opt(var2);
            if (var4 instanceof JSONObject) {
                var4 = a((JSONObject)var4);
            } else if (var4 instanceof JSONArray) {
                var4 = a((JSONArray)var4);
            }

            var1.add(var4);
        }

        return var1;
    }
}
