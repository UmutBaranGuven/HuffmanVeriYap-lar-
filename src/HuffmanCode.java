//UMUT BARAN GÜVEN 02195076017
import java.util.Comparator;  
import java.util.HashMap;  
import java.util.Map;  
import java.util.PriorityQueue;  


class Node  
{  
    //Tanımlamaları burda yaptım
    Character ch;  
    Integer freq;  
    Node left = null;   
    Node right = null;   
    //creating a constructor of the Node class  
    Node(Character ch, Integer freq)  
    {  
        this.ch = ch;  
        this.freq = freq;  
    }  
    // Düğümler için constructor oluşturdum 
    public Node(Character ch, Integer freq, Node left, Node right)  
    {  
        this.ch = ch;  
        this.freq = freq;  
        this.left = left;  
        this.right = right;  
    }  
}  

public class HuffmanCode  
{  
    //Çalışan huffman dizisi 
    public static void createHuffmanTree(String text)  
    {  
         
        if (text == null || text.length() == 0)   
        {  
            return;  
        }  
           
        Map<Character, Integer> freq = new HashMap<>();  
          
        for (char c: text.toCharArray())   
        {  
               
            freq.put(c, freq.getOrDefault(c, 0) + 1);  
        }  
        //Huffman dizisi için düğümleri kayıt ortamına alan öncelikli kuyruk oluşturdum.
           
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(l -> l.freq));  
          
        for (var entry: freq.entrySet())   
        {  
            //Yaprak düğümü oluşturup kuyruğa ekleme 
            pq.add(new Node(entry.getKey(), entry.getValue()));  
        }  
          
        while (pq.size() != 1)  
        {  
            //yüksek öncelikli düğümleri sıradan çıkartmak 
            Node left = pq.poll();  
            Node right = pq.poll();  
              
            int sum = left.freq + right.freq;  
            //sağ ve sol düğümleri toplayıp yeni düğüm oluşturma
            pq.add(new Node(null, sum, left, right));  
        }  
        
        Node root = pq.peek();  
       
        Map<Character, String> huffmanCode = new HashMap<>();  
        encodeData(root, "", huffmanCode);  
        
        System.out.println("Karakterlerin Huffman Kodlari: " + huffmanCode);  
        
        System.out.println("ilk dizi: " + text);  
        StringBuilder sb = new StringBuilder();  
        //karakter dizisi için döngü oluşturmak
        for (char c: text.toCharArray())   
        {  
            //karakterleri alıp kodlanmış dizinin çıktısını alır
            sb.append(huffmanCode.get(c));  
        }   
        System.out.println("Kodlanmis dizi: " + sb);  
        System.out.print("Kodu cozulmus dizi: ");  
        if (isLeaf(root))  
        {  
            
            while (root.freq-- > 0)   
            {  
                System.out.print(root.ch);  
            }  
        }  
        else   
        {  
             
            int index = -1;  
            while (index < sb.length() - 1)   
            {  
                index = decodeData(root, index, sb);  
            }  
        }  
    }  

    //Verileri kodladığım fonksiyonn  
    public static void encodeData(Node root, String str, Map<Character, String> huffmanCode)  
    {  
        if (root == null)   
        {  
            return;  
        }  
        //düğümün yaprak düğüm olup olmadığı kontrol etmek 
        if (isLeaf(root))   
        {  
            huffmanCode.put(root.ch, str.length() > 0 ? str : "1");  
        }  
        encodeData(root.left, str + '0', huffmanCode);  
        encodeData(root.right, str + '1', huffmanCode);  
    }  
    public static int decodeData(Node root, int index, StringBuilder sb)  
    {  
        //Kök düğümünün boş mu değil mi olduğunu kontrol eder
        if (root == null)   
        {  
            return index;  
        }   
        //düğümün yaprak düğüm olup olmadığını kontrol eder
        if (isLeaf(root))  
        {  
            System.out.print(root.ch);  
            return index;  
        }  
        index++;   
        root = (sb.charAt(index) == '0') ? root.left : root.right;  
        index = decodeData(root, index, sb);  
        return index;  
    }  
    //Tek bir düğüm olup olmadığını kontrol eder
    public static boolean isLeaf(Node root)   
    {  
        //Her iki durum da doğruysa true değerini döndürür
        return root.left == null && root.right == null;  
    }  
      
    public static void main(String args[])  
    {  
        String text = "javatpoint";  
         
        createHuffmanTree(text);  
    }  
}  