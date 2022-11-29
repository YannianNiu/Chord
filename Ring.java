package chord;

public class Ring {
    Node firNode;
    Node lastNode;
    int max_nodes;
    int num_nodes;

    public Ring(){
        this.max_nodes=(int) Math.pow(2, 21);
        this.num_nodes=0;
        this.firNode=null;
        this.lastNode=null;
    }

    // 插入节点
    public void insert_node(int idx){
        Node n =get_Node(idx);
        if(this.num_nodes==0){
            this.firNode = n;
            this.lastNode = this.firNode;
        }else if(this.num_nodes==1){
            if(idx > this.firNode.index){
                this.firNode.joinRightNode(n);
                this.lastNode = n;
            }else{
                this.firNode.joinLeftNode(n);
                this.lastNode = this.firNode;
                this.firNode = n;
            }
        }else{
            Node p = this.firNode;
            while(p.index<idx && p!=this.lastNode ){
                p = p.successorNode;
            }
            if(p.index>idx){
                p.joinLeftNode(n);
                if(p==this.firNode){
                    this.firNode = n;
                }
            }else if(p==this.lastNode){
                p.joinRightNode(n);
                this.lastNode = n;
            }
        }
        this.num_nodes++;
    }

    public void insert_node_ip(int idx, String s){
        Node n =get_Node_ip(idx, s);
        if(this.num_nodes==0){
            this.firNode = n;
            this.lastNode = this.firNode;
        }else if(this.num_nodes==1){
            if(idx > this.firNode.index){
                this.firNode.joinRightNode(n);
                this.lastNode = n;
            }else{
                this.firNode.joinLeftNode(n);
                this.lastNode = this.firNode;
                this.firNode = n;
            }
        }else{
            Node p = this.firNode;
            while(p.index<idx && p!=this.lastNode ){
                p = p.successorNode;
            }
            if(p.index>idx){
                p.joinLeftNode(n);
                if(p==this.firNode){
                    this.firNode = n;
                }
            }else if(p==this.lastNode){
                p.joinRightNode(n);
                this.lastNode = n;
            }
        }
        this.num_nodes++;
    }
    
    public void update_finger_table(){
        Node p = this.firNode;
        if(p==null){
            return;
        }
        while(p!=this.lastNode){
            p.updateSearchList();
            p=p.successorNode;
        }
        this.lastNode.updateSearchList();
    }

    public Node get_Node(int id) {
        return Node.createNode(id);
    }
    
    public Node get_Node_ip(int id, String ip) {
        return Node.createNode(id, ip);
    }

    // 删除节点
    public boolean delete_node(int index){
        Node p = this.firNode.search_node(index, 1);
        if(p==null || p.index != index){
            return false;
        }
        if(this.firNode == p && this.num_nodes>1){
            this.firNode = p.successorNode;
        }else if(this.num_nodes<=1){
            this.firNode = null;
            this.lastNode = null;
        }else if(this.lastNode ==p && this.num_nodes>1){
            this.lastNode = p.predecessNode;
        }
        p.deleteNode();
        this.num_nodes --;
        update_finger_table();
        return true;
    }

    public void print_node_file(int idx){
        Node p = this.firNode.search_node(idx,1);
        if(p!=null) {
        	p.print_file();
        }
    }
    
    public void print_node_filenum(){
        Node p = this.firNode;
	    while(p!= null && p!= this.lastNode) {
	    	System.out.println(p.index+": "+p.print_file_num());
	    	p=p.successorNode;
	    }
	    System.out.println(this.lastNode.index+": "+this.lastNode.print_file_num());
    }

    public int[] get_node_key() {
    	Node p = this.firNode;
    	int[] nums =new int[this.num_nodes];
    	int i=0;
    	while(hasNext(p)) {
    		nums[i] = p.print_file_num();
    		i++;
    		p=p.successorNode;
    	}
    	nums[i]=p.print_file_num();
    	return nums;
    }
    
    public boolean hasNext(Node p) {
    	if(p == this.lastNode) {
    		return false;
    	}else {
    		return true;
    	}
    }
    
    public boolean save_node_key(int idx, String s){
        Node p = this.firNode.search_node(idx,1);
        if(p!=null) {
        	p.save_file(s);
        	return true;
        }
        return false;
    }
    
    public void search_node_file(int idx, String s){
        this.firNode.search_file(idx, s);
    }
    
    public int search_node_path(int idx, String s) {
    	return this.firNode.search_file_path(idx, s);
    }
    
    public int get_node_key_num(int idx) {
    	Node p = this.firNode.search_node(idx, 1);
    	return p.print_file_num();
    }

    public void print_ring(){
        Node p = this.firNode;
        while(p!=this.lastNode){
            System.out.println("index: "+p.index+"  ip: "+p.ip);
            p=p.successorNode;
        }
        System.out.println("index: "+p.index+"  ip: "+p.ip);
    }
}
