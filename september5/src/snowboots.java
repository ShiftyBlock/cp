/*
ID: davidzh8
PROG: subset
LANG: JAVA
 */

import java.io.*;
import java.util.*;
import java.lang.*;

public class snowboots {

    static long startTime = System.nanoTime();

    public static void main(String[] args) throws Exception {
        boolean debug = !(true);
        FastScanner sc = new FastScanner("snowboots.in", debug);
        FastWriter pw = new FastWriter("snowboots.out", debug);
        int N= sc.ni();
        int M= sc.ni();
        Integer[] tidx= new Integer[N];
        Integer[] bidx= new Integer[M];
        int[] tile= new int[N];
        for (int i = 0; i < N; i++) {
            tile[i]= sc.ni();
            tidx[i]=i;
        }
        Comparator<Integer> sorttile= new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return tile[o1]-tile[o2];
            }
        };
        Arrays.sort(tidx, sorttile);
        int[] snow= new int[M];
        int[] dist= new int[M];
        for (int i = 0; i < M; i++) {
            snow[i]= sc.ni(); dist[i]= sc.ni();
            bidx[i]=i;
        }
        Comparator<Integer> sortboot= new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return snow[o2]-snow[o1];
            }
        };
        Arrays.sort(bidx, sortboot);
        int left[]= new int[N];
        int[] right= new int[N];
        for (int i = 0; i < N; i++) {
            left[i]= i-1;
            right[i]=i+1;
        }
        int[] res= new int[M];
        int j=N-1;
        int worst=1;
        for (int i = 0; i < M; i++) {
            int cursnow=snow[bidx[i]];
            int curdist= dist[bidx[i]];
            while(j>=0 && tile[tidx[j]]>cursnow){
                int tileindex= tidx[j];
                //swap right value from cur to next cur
                right[left[tileindex]]= right[tileindex];
                left[right[tileindex]]= left[tileindex];
                worst= Math.max(worst, right[tileindex]-left[tileindex]);
                j--;
            }
            res[bidx[i]]=worst>curdist? 0:1;
        }
        for (int i = 0; i < M; i++) {
            pw.println(res[i]);
        }

        long endTime = System.nanoTime();
        System.out.println("Execution Time: " + (endTime - startTime) / 1e9 + " s");
        pw.close();
    }


    static class Pair implements Comparable<Pair> {
        int a;
        int b;

        public Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public int compareTo(Pair obj) {
            if (obj.a == a) {
                return b - obj.b;
            } else return a - obj.a;
        }
    }


    static class djset {
        int N;
        int[] parent;

        // Creates a disjoint set of size n (0, 1, ..., n-1)
        public djset(int n) {
            parent = new int[n];
            N = n;
            for (int i = 0; i < n; i++)
                parent[i] = i;
        }

        public int find(int v) {

            // I am the club president!!! (root of the tree)
            if (parent[v] == v) return v;

            // Find my parent's root.
            int res = find(parent[v]);

            // Attach me directly to the root of my tree.
            parent[v] = res;
            return res;
        }

        public boolean union(int v1, int v2) {

            // Find respective roots.
            int rootv1 = find(v1);
            int rootv2 = find(v2);

            // No union done, v1, v2 already together.
            if (rootv1 == rootv2) return false;

            // Attach tree of v2 to tree of v1.
            parent[rootv2] = rootv1;
            return true;
        }


    }

    static class FastScanner {
        InputStream dis;
        byte[] buffer = new byte[1 << 17];
        int pointer = 0;

        public FastScanner(String fileName, boolean debug) throws Exception {
            if (debug) dis = System.in;
            else dis = new FileInputStream(fileName);
        }

        int ni() throws Exception {
            int ret = 0;

            byte b;
            do {
                b = nextByte();
            } while (b <= ' ');
            boolean negative = false;
            if (b == '-') {
                negative = true;
                b = nextByte();
            }
            while (b >= '0' && b <= '9') {
                ret = 10 * ret + b - '0';
                b = nextByte();
            }

            return (negative) ? -ret : ret;
        }

        long nl() throws Exception {
            long ret = 0;

            byte b;
            do {
                b = nextByte();
            } while (b <= ' ');
            boolean negative = false;
            if (b == '-') {
                negative = true;
                b = nextByte();
            }
            while (b >= '0' && b <= '9') {
                ret = 10 * ret + b - '0';
                b = nextByte();
            }

            return (negative) ? -ret : ret;
        }

        byte nextByte() throws Exception {
            if (pointer == buffer.length) {
                dis.read(buffer, 0, buffer.length);
                pointer = 0;
            }
            return buffer[pointer++];
        }

        String next() throws Exception {
            StringBuffer ret = new StringBuffer();

            byte b;
            do {
                b = nextByte();
            } while (b <= ' ');
            while (b > ' ') {
                ret.appendCodePoint(b);
                b = nextByte();
            }

            return ret.toString();
        }
    }

    static class FastWriter {
        public PrintWriter pw;

        public FastWriter(String name, boolean debug) throws IOException {
            if (debug) {
                pw = new PrintWriter(System.out);
            } else {
                pw = new PrintWriter(new BufferedWriter(new FileWriter(new File(name))));
            }
        }

        public void println(Object text) {
            pw.println(text);
        }

        public void print(Object text) {
            pw.print(text);
        }

        public void close() {
            pw.close();
        }

        public void flush() {
            pw.flush();
        }
    }

}