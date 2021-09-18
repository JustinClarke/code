//Java Program
//Rearrange linked list in circular order
public class LinkedList {

  static class Node {
    int data;
    Node next;
  }

  public Node head, back;

  // Class constructors
  LinkedList() {
    head = null;
    back = null;
  }

  // insert element
  public void insert(int value) {
    // Create node
    Node node = new Node();
    node.data = value;
    node.next = null;
    if (head == null) {
      head = node;
    } else {
      Node temp = head;
      // find last node
      while (temp.next != null) {
        temp = temp.next;
      }
      temp.next = node;
    }

  }

  // Display all Linked List elements
  public void display() {
    if (head != null) {
      Node temp = head;
      while (temp != null) {
        System.out.print("  " + temp.data);
        temp = temp.next;
      }
      System.out.println();
    } else {
      System.out.println("Empty Linked list");
    }
  }

  public void arrange(Node head) {

    if (head == null || back == null) {

      return;
    }

    arrange(head.next);

    if (back != null) {

      if (back == head || back.next == head) {

        back = null;
        head.next = null;
        return;
      }
      // Change pointer link
      head.next = back.next;
      back.next = head;
      back = head.next;
    }
  }

  public void spiral_view() {

    back = head;

    arrange(head);
  }

  public static void main(String[] args) {

    LinkedList obj1 = new LinkedList();

    obj1.insert(1);
    obj1.insert(3);
    obj1.insert(5);
    obj1.insert(9);
    obj1.insert(7);
    obj1.insert(6);
    obj1.insert(4);
    obj1.insert(2);

    System.out.print("\nBefore spiral\n");
    // Display all node
    obj1.display();

    System.out.print("\nAfter spiral\n");

    obj1.spiral_view();
    // Display all node
    obj1.display();

  }
}