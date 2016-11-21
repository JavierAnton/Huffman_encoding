/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huffmanencode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Javier
 */
public class HuffmanEncode {
    
    /**
     * Creates the tree
     * @param map map
     * @return HuffmanTree
     */
    public static HuffmanTree buildTree(HashMap map){
        Set<String> keys = map.keySet();
                
        //We will create the first leaves to join them
        PriorityQueue<HuffmanTree> trees = new PriorityQueue<>();
        
        //At first we only have one Huffman leave for each 
        //element to encode
        for(String key : keys){
            System.out.println(key + " " + map.get(key));
            HuffmanLeaf leaf = new HuffmanLeaf((int)map.get(key), key);
            trees.offer(leaf);
        }
        
        assert trees.size() > 0; //Just in case we do not have leaves
        
        //Loop until we have only the header of the tree
        while(trees.size() > 1){
            //The two trees with laast freq
            HuffmanTree l = trees.poll();
            HuffmanTree r = trees.poll();
            
            //Pull the new combined node
            trees.offer(new HuffmanNode(l,r));
        }
        
        return trees.poll(); 
    }
    
    /**
     * Create and print the codes
     * @param tree Huffman tree
     */
    private static void getCodes(HashMap<String, String> codes,HuffmanTree tree, StringBuffer prefix) {
        assert tree != null;  // We do not pass an empty tree
        
        if(tree instanceof HuffmanLeaf){
            HuffmanLeaf leaf = (HuffmanLeaf)tree;
            codes.put(leaf.key, prefix.toString());
            
            //Print the simbol, frequency and the code of this leaf
            System.out.println(leaf.key + "\t" + leaf.frequency + "\t" + prefix);
        }
        
        if(tree instanceof HuffmanNode){
            HuffmanNode node = (HuffmanNode)tree;
            
            //Go to the left Node/Leaf
            prefix.append('0');
            getCodes(codes, node.left, prefix);
            prefix.deleteCharAt(prefix.length()-1);
            
            //Go to the right Node/Leaf
            prefix.append('1');
            getCodes(codes, node.right, prefix);
            prefix.deleteCharAt(prefix.length()-1);
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        HashMap<String, Integer> map = new HashMap();
        HashMap<String, String> codes = new HashMap<>();
        map.put("D", 30);
        map.put("K", 20);
        map.put("Q", 20);
        map.put("J", 15);
        map.put("10", 10);
        map.put("9", 5);
        
        HuffmanTree tree = buildTree(map);
        System.out.println("SYMBOL\tWEIGHT\tHUFFMAN CODE");
        getCodes(codes, tree, new StringBuffer());
        System.out.println(codes.keySet());
        
        /*Second part, open the file and encode the info*/
        int elements = 0; //Elements coded
        StringBuffer str = new StringBuffer(); //The whole file coded
        List<Integer> list = new ArrayList<>();
        File file = new File("huffman.txt");
        BufferedReader reader = null;
        
        try {
            reader = new BufferedReader(new FileReader(file));
            String text = reader.readLine(); 
            double size = text.length();
            char simbol;
                    
            for(int i=0; i<size; i++){
                simbol = text.charAt(i);
                if (simbol == '1'){
                    str.append(codes.get("10"));
                    i++;
                    size--;
                }else{
                    //System.out.println(codes.get( String.valueOf(simbol) ));
                    str.append(codes.get( String.valueOf(simbol) ));
                }
                elements++;
            }
                
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }catch (IOException ex) {
            ex.printStackTrace();
        }
        
        System.out.println(str);
        
        //Now we find the encode value
        double code_size = (double)str.length();
        double normal_code_size = (double)elements * 3; 
        double value = normal_code_size / code_size;
        
        System.out.println("Compresion factor = "+value);
        
    }
    
}

class HuffmanTree implements Comparable<HuffmanTree>{
    public final int frequency; //Tre frequency of this tree
    public HuffmanTree(int freq){this.frequency = freq;}    

    @Override //Compares to another tree
    public int compareTo(HuffmanTree tree) {
        return frequency - tree.frequency;
    }
}

class HuffmanLeaf extends HuffmanTree{
    public final String key; //The key we want to encode
    public HuffmanLeaf(int freq, String key){ 
        super(freq);
        this.key = key;
    }
}

class HuffmanNode extends HuffmanTree{
    public final HuffmanTree left, right; //Subtrees
    public HuffmanNode(HuffmanTree l, HuffmanTree r){
        super(l.frequency + r.frequency);
        this.left = l;
        this.right = r;
    }
}