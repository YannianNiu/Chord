package chord;

import java.util.ArrayList;
import java.util.List;

public class Node {
    // 以这个节点做服务开始?
    Node predecessNode;
    Node successorNode;
    int[] fingerStart;
    Node[] fingerTable;
    int index;
    List<String> fileList;
    String ip;

    public Node(int index, int table){
        this.index = index;
        this.predecessNode = this;
        this.successorNode = this;
        this.fingerStart = createFingerStart(table);
        this.fingerTable = new Node[table];
        this.fileList = new ArrayList<>();
        this.ip = null;
    }

    public Node(int index, String s, int table){
        this.index = index;
        this.predecessNode = this;
        this.successorNode = this;
        this.fingerStart = createFingerStart(table);
        this.fingerTable = new Node[table];
        this.fileList = new ArrayList<>();
        this.ip = s;
    }

    // 创建node
    public static Node createNode(int index){
        return new Node(index, 20);
    }

    public static Node createNode(int index, String s){
        return new Node(index, s, 20);
    }
    
    private int[] createFingerStart(int x){
        int[] start = new int[x];
        for(int i=0; i<x; i++){
            start[i] = (int) (this.index+Math.pow(2, i));
        }
        return start;
    }

    // 在当前节点的右边加入节点
    public void joinRightNode(Node node){
        Node p = this.successorNode;
        node.successorNode = p;
        this.successorNode = node;
        node.predecessNode = this;
        if(p != null){
            p.predecessNode = node;
        }
    }

    // 在当前节点的左边加入节点node
    public void joinLeftNode(Node node){
        Node p =this.predecessNode;
        this.predecessNode = node;
        node.successorNode = this;
        node.predecessNode = p;
        if(p!= null){
            p.successorNode=node;
        }
    }

    // 删除节点后需要调用全局的查找表重建
    public void deleteNode(){
        Node front = this.predecessNode;
        Node after = this.successorNode;
        front.successorNode = after;
        after.predecessNode = front;
    }

    // 更新FingerTable
    public void updateSearchList(){
        Node p =this.successorNode;
        int i=0, temp=this.index;
        while(i<this.fingerTable.length){
            // 环
            // this.fingerStart[i] = this.index+Math.pow(2, i);
            if(p.index >= this.fingerStart[i]){
                this.fingerTable[i]=p;
                // temp = p.index;
                i++;
            }else if(p.index <= temp){ 
                this.fingerTable[i]=p;
                // temp=p.index; //不能变化标示量了
                i++;
            }else{
                p = p.successorNode;
            }
        }
    }

    // 查找idx节点
    public Node search_node(int idx, int times){
//    	if(times>20) {
//    		System.out.println("key:"+idx + " node:"+this.index);
//    	}
        if(this.index == idx){
            // System.out.println("Success find file "+s+" in server "+this.index);
            return this;
        }else if(this.index>idx && this.predecessNode.index<idx){
            // System.out.println("Success find file "+s+" in server "+this.index);
            return this;
        }else if(this.index>idx && this.predecessNode.index>=this.index){
            // System.out.println("Success find file "+s+" in server "+this.index);
            return this;
        }else if(this.index<idx && this.predecessNode.index>=this.index && this.predecessNode.index<idx){
            // System.out.println("Success find file "+s+" in server "+this.index);
            return this;
        }else if(this.index < idx){
            int i=this.fingerTable.length-1;
            // int pid = this.index+Math.pow(2, i);
            int pid = this.fingerStart[i];
            Node p = this.fingerTable[i];
            while(pid > idx && i>0){
                i--;
                p = this.fingerTable[i];
                // pid = this.index+Math.pow(2, i);
                pid = this.fingerStart[i];
            }
            return p.search_node(idx, times+1);
        }
        return null;
    }
    
    public int search_node_path(int idx, int times){
        if(this.index == idx){
            // System.out.println("Success find file "+s+" in server "+this.index);
            return times;
        }else if(this.index>idx && this.predecessNode.index<idx){
            // System.out.println("Success find file "+s+" in server "+this.index);
            return times;
        }else if(this.index>idx && this.predecessNode.index>=this.index){
            // System.out.println("Success find file "+s+" in server "+this.index);
            return times;
        }else if(this.index<idx && this.predecessNode.index>=this.index && this.predecessNode.index<idx){
            // System.out.println("Success find file "+s+" in server "+this.index);
            return times;
        }else if(this.index < idx){
            int i=this.fingerTable.length-1;
            // int pid = this.index+Math.pow(2, i);
            int pid = this.fingerStart[i];
            Node p = this.fingerTable[i];
            while(pid > idx && i>0){
                i--;
                p = this.fingerTable[i];
                // pid = this.index+Math.pow(2, i);
                pid = this.fingerStart[i];
            }
            return p.search_node_path(idx, times+1);
        }
        return 0;
    }

    // 文件查找
    public Node search_file(int idx, String s){
        Node p = this.search_node(idx, 1);
        if(p==null) {
        	System.out.println("Failed to find file");
        	return p;
        }
        System.out.println("Success find file "+s+" in server "+p.index);
        return p;
    }
    
    public int search_file_path(int idx, String s){
        int times = this.search_node_path(idx, 1);
        return times;
    }

    public void save_file(String s){
        this.fileList.add(s);
    }

    public void print_file(){
        System.out.println("In node " + this.index+':');
        for(int i=0; i<this.fileList.size(); i++){
            System.out.println(this.fileList.get(i));
        }
    }
    
    public int print_file_num() {
//    	System.out.println(this.fileList.size());
    	return this.fileList.size();
    }
}
