import java.util.*;

class HuffmanCoder {
    HashMap<Character, String> encoder;
    HashMap<String, Character> decoder;

    private class Node implements Comparable<Node> {
        Character data;
        int cost; // frequency
        Node left;
        Node right;

        public Node(Character data, int cost) {
            this.data = data;
            this.cost = cost;
            this.left = null;
            this.right = null;
        }

        @Override
        public int compareTo(Node other) {
            return this.cost - other.cost;  // Compare based on cost (frequency)
        }
    }

    public HuffmanCoder(String feeder) throws Exception {
        HashMap<Character, Integer> fmap = new HashMap<>();

        // Build the frequency map
        for (int i = 0; i < feeder.length(); i++) {
            char cc = feeder.charAt(i);
            fmap.put(cc, fmap.getOrDefault(cc, 0) + 1);
        }

        // PriorityQueue with a custom comparator to ensure min-heap property
        PriorityQueue<Node> minHeap = new PriorityQueue<>();

        // Insert all nodes into the priority queue (min-heap)
        Set<Map.Entry<Character, Integer>> entrySet = fmap.entrySet();
        for (Map.Entry<Character, Integer> entry : entrySet) {
            Node node = new Node(entry.getKey(), entry.getValue());
            minHeap.offer(node);  // Add to the priority queue
        }

        // Build the Huffman tree
        while (minHeap.size() > 1) {
            Node first = minHeap.poll();  // Remove and return the smallest element
            Node second = minHeap.poll(); // Remove the next smallest element

            Node newNode = new Node('\0', first.cost + second.cost);
            newNode.left = first;
            newNode.right = second;

            minHeap.offer(newNode);  // Insert the new internal node back into the heap
        }

        Node ft = minHeap.poll();  // Get the root node of the Huffman tree

        this.encoder = new HashMap<>();
        this.decoder = new HashMap<>();

        this.initEncoderDecoder(ft, "");//osf:- output so far 
    }

    private void initEncoderDecoder(Node node, String osf) {
        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null) {
            this.encoder.put(node.data, osf);
            this.decoder.put(osf, node.data);
        }
        initEncoderDecoder(node.left, osf + "0");
        initEncoderDecoder(node.right, osf + "1");
    }

    public String encode(String source) {
        StringBuilder ans = new StringBuilder();

        // Encode the source string using the encoder map
        for (int i = 0; i < source.length(); i++) {
            ans.append(encoder.get(source.charAt(i)));
        }

        return ans.toString();
    }

    public String decode(String codedString) {
        StringBuilder key = new StringBuilder();  // Use StringBuilder for the key
        StringBuilder ans = new StringBuilder(); // Use StringBuilder for the result

        // Process each character in the coded string
        for (int i = 0; i < codedString.length(); i++) {
            key.append(codedString.charAt(i));  // Append character to the key

            // If the key matches a code in the decoder map
            if (decoder.containsKey(key.toString())) {
                ans.append(decoder.get(key.toString()));  // Append decoded character
                key.setLength(0);  // Reset the key
            }
        }

        return ans.toString();  // Return the decoded string
    }

    public static void main(String[] args) throws Exception {
        // Example usage of HuffmanCoder
        String input = "this is an example for huffman encoding";
        HuffmanCoder huffmanCoder = new HuffmanCoder(input);

        // Encode the input string
        String encodedString = huffmanCoder.encode(input);
        System.out.println("Encoded String: " + encodedString);

        // Decode the encoded string
        String decodedString = huffmanCoder.decode(encodedString);
        System.out.println("Decoded String: " + decodedString);
    }
}
