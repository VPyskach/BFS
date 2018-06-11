package com.coursework.univer.bfs;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private static int vertexCount;
    private static int[][] graphMatrix;
    private static List<Vertex> verticesKoord;

    public Graph() {
        graphMatrix = new int[1000][1000];
        verticesKoord = new ArrayList<>();
        vertexCount = 0;
    }

    public void addVertex(float x, float y) {
        vertexCount++;
        verticesKoord.add(new Vertex(x, y));
    }

    public void addEdge(int firstVertex, int secondVertex) {
        graphMatrix[firstVertex][secondVertex] = 1;
        graphMatrix[secondVertex][firstVertex] = 1;
    }

    public void removeVertex(int i) {
        verticesKoord.remove(i);
        for (int j = 0; j < vertexCount; j++) {
            for (int k = i; k < vertexCount; k++) {
                graphMatrix[j][k] = graphMatrix[j][k + 1];
            }
        }
        for (int j = i; j < vertexCount; j++) {
            for (int k = 0; k < vertexCount; k++) {
                graphMatrix[j][k] = graphMatrix[j + 1][k];
            }
        }
        vertexCount--;
    }

    public void removeEdge(int firstVertex, int secondVertex) {
        graphMatrix[firstVertex][secondVertex] = 0;
        graphMatrix[secondVertex][firstVertex] = 0;
    }

    public static int[][] getGraphMatrix() {
        return graphMatrix;
    }

    public static List<Vertex> getVerticesKoord() {
        return verticesKoord;
    }

    public static int getVertexCount() {
        return vertexCount;
    }

    public void setVertexKoord(int id, float x, float y) {
        verticesKoord.get(id).setX(x);
        verticesKoord.get(id).setY(y);
    }

    public static int[] getBfs(int startVertexId) {
        int[] result;
        boolean[] used = new boolean[vertexCount];
        result = new int[vertexCount];
        int qH = 0;
        int qT = 0;
        used[startVertexId] = true;
        result[qT++] = startVertexId;
        while (qH < qT) {
            startVertexId = result[qH++];
            for (int nv = 0; nv < vertexCount; nv++) {
                if (!used[nv] && graphMatrix[startVertexId][nv] == 1) {
                    used[nv] = true;
                    result[qT++] = nv;
                }
            }
        }
        return result;
    }

    public int D(int s, int f) {
        if(f < s){
            return D(f,s);
        }
        int[] dist = new int[999];
        int[] q = new int[999];
        int v, j, r = 0;
        boolean[] used = new boolean[999];
        for (int i = 1; i <= vertexCount; i++)
            used[s] = true;
        q[0] = s;
        j = 0;
        while (j <= r) {
            v = q[j];
            for (int i = 1; i <= vertexCount; i++) {
                if (graphMatrix[v][i] == 1 && !used[i]) {
                    r++;
                    dist[i] = dist[v] + 1;
                    q[r] = i;
                    used[i] = true;
                }
            }
            j++;
        }
        return dist[f];
    }
}
