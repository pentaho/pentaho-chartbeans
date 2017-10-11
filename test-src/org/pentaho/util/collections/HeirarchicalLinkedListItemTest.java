/*
* Copyright 2002 - 2017 Hitachi Vantara.  All rights reserved.
* 
* This software was developed by Hitachi Vantara and is provided under the terms
* of the Mozilla Public License, Version 1.1, or any later version. You may not use
* this file except in compliance with the license. If you need a copy of the license,
* please go to http://www.mozilla.org/MPL/MPL-1.1.txt. TThe Initial Developer is Pentaho Corporation.
*
* Software distributed under the Mozilla Public License is distributed on an "AS IS"
* basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to
* the license for the specific language governing your rights and limitations.
*/

package org.pentaho.util.collections;

import junit.framework.TestCase;

public class HeirarchicalLinkedListItemTest extends TestCase {
  /**
   * Tests the <code>addChild()</code> method.
   */
  @SuppressWarnings("nls")
  public void testAddChild() {
    final HeirarchicalLinkedListItem parent = new HeirarchicalLinkedListItem();
    try {
      parent.addChildItem(null);
      fail("Should not be able to add a null child");
    } catch (IllegalArgumentException iae) {
      // correct
    }

    final HeirarchicalLinkedListItem child1 = new HeirarchicalLinkedListItem();
    parent.addChildItem(child1);
    assertEquals("child should have the parent set automatically", parent, child1.getParentItem());
    assertEquals("parent's first child should be the only child", child1, parent.getFirstChildItem());
    assertEquals("parent's last child should be the only child", child1, parent.getLastChildItem());
    assertNull("endpoint problem", child1.getPreviousItem());
    assertNull("endpoint problem", child1.getNextItem());

    final HeirarchicalLinkedListItem child2 = new HeirarchicalLinkedListItem();
    parent.addChildItem(child2);
    assertEquals("parent's first child should not have changed", child1, parent.getFirstChildItem());
    assertEquals("parent's last child should be the new child", child2, parent.getLastChildItem());
    assertEquals("new child's parent should be set", parent, child2.getParentItem());
    assertNull("endpoint problem", child1.getPreviousItem());
    assertEquals("incorrect child order", child2, child1.getNextItem());
    assertEquals("incorrect child order", child1, child2.getPreviousItem());
    assertNull("endpoint problem", child2.getNextItem());

    final HeirarchicalLinkedListItem child3 = new HeirarchicalLinkedListItem();
    parent.addChildItem(child3);
    assertEquals("parent's first child should not have changed", child1, parent.getFirstChildItem());
    assertEquals("parent's last child should be the new child", child3, parent.getLastChildItem());
    assertEquals("new child's parent should be set", parent, child3.getParentItem());
    assertNull("endpoint problem", child1.getPreviousItem());
    assertEquals("incorrect child order", child2, child1.getNextItem());
    assertEquals("incorrect child order", child1, child2.getPreviousItem());
    assertEquals("incorrect child order", child3, child2.getNextItem());
    assertEquals("incorrect child order", child2, child3.getPreviousItem());
    assertNull("endpoint problem", child3.getNextItem());

    final HeirarchicalLinkedListItem badChild = new HeirarchicalLinkedListItem();
    child3.addChildItem(badChild);
    try {
      parent.addChildItem(badChild);
      fail("can't add a child whose parent is already set");
    } catch (IllegalStateException ise) {
      // correct
    }

    // Make sure nothing changed
    assertNull("endpoint problem", child1.getPreviousItem());
    assertEquals("incorrect child order", child2, child1.getNextItem());
    assertEquals("incorrect child order", child1, child2.getPreviousItem());
    assertEquals("incorrect child order", child3, child2.getNextItem());
    assertEquals("incorrect child order", child2, child3.getPreviousItem());
    assertNull("endpoint problem", child3.getNextItem());

    // Check from the parent's point of view
    HeirarchicalLinkedListItem test = parent.getFirstChildItem();
    assertEquals("First item incorrect", child1, test);
    test = test.getNextItem();
    assertEquals("Second item incorrect", child2, test);
    test = test.getNextItem();
    assertEquals("Third item incorrect", child3, test);
    test = test.getNextItem();
    assertNull("Should not be a 4th item", test);
  }

  /**
   * Tests the <code>insertBefore()</code> method
   */
  @SuppressWarnings("nls")
  public void testInsertBefore() {
    final HeirarchicalLinkedListItem parent = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child1 = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child2 = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child3 = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem badChild = new HeirarchicalLinkedListItem();

    try {
      parent.insertBefore(null, child1);
      fail("insertBefore should fail with a null child node");
    } catch (IllegalArgumentException iae) {
      // correct
    }

    try {
      parent.insertBefore(child1, null);
      fail("inserBefore should fail with a null target node");
    } catch (IllegalArgumentException iae) {
      // correct
    }

    try {
      parent.insertBefore(child1, child2);
      fail("insertBefore should fail if the target node can not be found");
    } catch (IllegalArgumentException iae) {
      // correct
    }

    // Add 1 child to the list
    parent.addChildItem(child3);

    // Add a child in front of that child [child1 -> child3]
    parent.insertBefore(child1, child3);
    assertEquals("child does not have the correct parent", parent, child1.getParentItem());
    assertNull("endpoint problem", child1.getPreviousItem());
    assertEquals("incorrect child order", child3, child1.getNextItem());
    assertEquals("incorrect child order", child1, child3.getPreviousItem());
    assertNull("endpoint problem", child3.getNextItem());

    // Add child2 between child1 and child3 [child1 -> child2 -> child3]
    parent.insertBefore(child2, child3);
    assertEquals("child does not have the correct parent", parent, child1.getParentItem());
    assertNull("endpoint problem", child1.getPreviousItem());
    assertEquals("incorrect child order", child2, child1.getNextItem());
    assertEquals("incorrect child order", child1, child2.getPreviousItem());
    assertEquals("incorrect child order", child3, child2.getNextItem());
    assertEquals("incorrect child order", child2, child3.getPreviousItem());
    assertNull("endpoint problem", child3.getNextItem());

    // Create a bad child
    child1.addChildItem(badChild);
    try {
      parent.insertBefore(badChild, child3);
      fail("Can't add a child when it already has a different parent");
    } catch (IllegalStateException ise) {
      // correct
    }

    // Make sure nothing in the list changed
    assertEquals("children do not have the correct parent", parent, child1.getParentItem());
    assertEquals("all children should have the same parent", parent, child2.getParentItem());
    assertEquals("all children should have the same parent", parent, child3.getParentItem());
    assertNull("endpoint problem", child1.getPreviousItem());
    assertEquals("incorrect child order", child2, child1.getNextItem());
    assertEquals("incorrect child order", child1, child2.getPreviousItem());
    assertEquals("incorrect child order", child3, child2.getNextItem());
    assertEquals("incorrect child order", child2, child3.getPreviousItem());
    assertNull("endpoint problem", child3.getNextItem());
  }

  /**
   * Tests the <code>insertAfter()</code> method
   */
  @SuppressWarnings("nls")
  public void testInsertAfter() {
    final HeirarchicalLinkedListItem parent = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child1 = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child2 = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child3 = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem badChild = new HeirarchicalLinkedListItem();

    try {
      parent.insertAfter(null, child1);
      fail("insertAfter should fail with a null child node");
    } catch (IllegalArgumentException iae) {
      // correct
    }

    try {
      parent.insertAfter(child1, null);
      fail("insertAfter should fail with a null target node");
    } catch (IllegalArgumentException iae) {
      // correct
    }

    try {
      parent.insertAfter(child2, child1);
      fail("insertAfter should fail if the target node can not be found");
    } catch (IllegalArgumentException iae) {
      // correct
    }

    // Add 1 child to the list
    parent.addChildItem(child1);

    // Add a child behind that child [child1 -> child3]
    parent.insertAfter(child3, child1);
    assertEquals("child does not have the correct parent", parent, child3.getParentItem());
    assertNull("endpoint problem", child1.getPreviousItem());
    assertEquals("incorrect child order", child3, child1.getNextItem());
    assertEquals("incorrect child order", child1, child3.getPreviousItem());
    assertNull("endpoint problem", child3.getNextItem());

    // Add child2 between child1 and child3 [child1 -> child2 -> child3]
    parent.insertAfter(child2, child1);
    assertEquals("child does not have the correct parent", parent, child2.getParentItem());
    assertNull("endpoint problem", child1.getPreviousItem());
    assertEquals("incorrect child order", child2, child1.getNextItem());
    assertEquals("incorrect child order", child1, child2.getPreviousItem());
    assertEquals("incorrect child order", child3, child2.getNextItem());
    assertEquals("incorrect child order", child2, child3.getPreviousItem());
    assertNull("endpoint problem", child3.getNextItem());

    // Create a bad child
    child1.addChildItem(badChild);
    try {
      parent.insertAfter(badChild, child3);
      fail("Can't add a child when it already has a different parent");
    } catch (IllegalStateException ise) {
      // correct
    }

    // Make sure nothing in the list changed
    assertEquals("children do not have the correct parent", parent, child1.getParentItem());
    assertEquals("all children should have the same parent", parent, child2.getParentItem());
    assertEquals("all children should have the same parent", parent, child3.getParentItem());
    assertNull("endpoint problem", child1.getPreviousItem());
    assertEquals("incorrect child order", child2, child1.getNextItem());
    assertEquals("incorrect child order", child1, child2.getPreviousItem());
    assertEquals("incorrect child order", child3, child2.getNextItem());
    assertEquals("incorrect child order", child2, child3.getPreviousItem());
    assertNull("endpoint problem", child3.getNextItem());
  }

  /**
   * Tests the <code>addFirstChild()</code> method.
   */
  @SuppressWarnings("nls")
  public void testAddFirstChild() {
    final HeirarchicalLinkedListItem parent = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child1 = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child2 = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child3 = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem badChild = new HeirarchicalLinkedListItem();

    try {
      parent.addFirstChildItem(null);
      fail("can't set null as a child");
    } catch (IllegalArgumentException iae) {
      // correct
    }

    parent.addFirstChildItem(child3);
    assertEquals("Invalid parent", parent, child3.getParentItem());
    assertEquals("Wrong first child", child3, parent.getFirstChildItem());
    assertEquals("Wrong last child", child3, parent.getLastChildItem());
    assertNull("Endpoint problem", child3.getPreviousItem());
    assertNull("Endpoint problem", child3.getNextItem());

    parent.addFirstChildItem(child2);
    assertEquals("Invalid parent", parent, child2.getParentItem());
    assertEquals("Wrong first child", child2, parent.getFirstChildItem());
    assertEquals("Wrong last child", child3, parent.getLastChildItem());
    assertNull("Endpoint problem", child2.getPreviousItem());
    assertEquals("incorrect child order", child3, child2.getNextItem());
    assertEquals("incorrect child order", child2, child3.getPreviousItem());
    assertNull("Endpoint problem", child3.getNextItem());

    parent.addFirstChildItem(child1);
    assertEquals("Invalid parent", parent, child1.getParentItem());
    assertEquals("Wrong first child", child1, parent.getFirstChildItem());
    assertEquals("Wrong last child", child3, parent.getLastChildItem());
    assertNull("Endpoint problem", child1.getPreviousItem());
    assertEquals("incorrect child order", child2, child1.getNextItem());
    assertEquals("incorrect child order", child1, child2.getPreviousItem());
    assertEquals("incorrect child order", child3, child2.getNextItem());
    assertEquals("incorrect child order", child2, child3.getPreviousItem());
    assertNull("Endpoint problem", child3.getNextItem());

    child3.addFirstChildItem(badChild);
    try {
      parent.addFirstChildItem(badChild);
      fail("can not add a child as a first child if it has a different parent");
    } catch (IllegalStateException ise) {
      // correct
    }

    // Make sure nothing got messed up
    assertEquals("Invalid parent", parent, child1.getParentItem());
    assertEquals("Wrong first child", child1, parent.getFirstChildItem());
    assertEquals("Wrong last child", child3, parent.getLastChildItem());
    assertNull("Endpoint problem", child1.getPreviousItem());
    assertEquals("incorrect child order", child2, child1.getNextItem());
    assertEquals("incorrect child order", child1, child2.getPreviousItem());
    assertEquals("incorrect child order", child3, child2.getNextItem());
    assertEquals("incorrect child order", child2, child3.getPreviousItem());
    assertNull("Endpoint problem", child3.getNextItem());
  }

  /**
   * Test the <code>addLastChild()</code> method
   */
  @SuppressWarnings("nls")
  public void testAddLastChild() {
    final HeirarchicalLinkedListItem parent = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child1 = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child2 = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child3 = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem badChild = new HeirarchicalLinkedListItem();

    try {
      parent.addLastChildItem(null);
      fail("can't set null as a child");
    } catch (IllegalArgumentException iae) {
      // correct
    }

    parent.addLastChildItem(child1);
    assertEquals("Invalid parent", parent, child1.getParentItem());
    assertEquals("Wrong first child", child1, parent.getFirstChildItem());
    assertEquals("Wrong last child", child1, parent.getLastChildItem());
    assertNull("Endpoint problem", child1.getPreviousItem());
    assertNull("Endpoint problem", child1.getNextItem());

    parent.addLastChildItem(child2);
    assertEquals("Invalid parent", parent, child2.getParentItem());
    assertEquals("Wrong first child", child1, parent.getFirstChildItem());
    assertEquals("Wrong last child", child2, parent.getLastChildItem());
    assertNull("Endpoint problem", child1.getPreviousItem());
    assertEquals("incorrect child order", child2, child1.getNextItem());
    assertEquals("incorrect child order", child1, child2.getPreviousItem());
    assertNull("Endpoint problem", child2.getNextItem());

    parent.addLastChildItem(child3);
    assertEquals("Invalid parent", parent, child3.getParentItem());
    assertEquals("Wrong first child", child1, parent.getFirstChildItem());
    assertEquals("Wrong last child", child3, parent.getLastChildItem());
    assertNull("Endpoint problem", child1.getPreviousItem());
    assertEquals("incorrect child order", child2, child1.getNextItem());
    assertEquals("incorrect child order", child1, child2.getPreviousItem());
    assertEquals("incorrect child order", child3, child2.getNextItem());
    assertEquals("incorrect child order", child2, child3.getPreviousItem());
    assertNull("Endpoint problem", child3.getNextItem());

    child3.addLastChildItem(badChild);
    try {
      parent.addLastChildItem(badChild);
      fail("can not add a child as a first child if it has a different parent");
    } catch (IllegalStateException ise) {
      // correct
    }

    // Make sure nothing got messed up
    assertEquals("Invalid parent", parent, child3.getParentItem());
    assertEquals("Wrong first child", child1, parent.getFirstChildItem());
    assertEquals("Wrong last child", child3, parent.getLastChildItem());
    assertNull("Endpoint problem", child1.getPreviousItem());
    assertEquals("incorrect child order", child2, child1.getNextItem());
    assertEquals("incorrect child order", child1, child2.getPreviousItem());
    assertEquals("incorrect child order", child3, child2.getNextItem());
    assertEquals("incorrect child order", child2, child3.getPreviousItem());
    assertNull("Endpoint problem", child3.getNextItem());
  }

  /**
   * Tests the <code>remove()</code> method
   */
  @SuppressWarnings("nls")
  public void testRemove() {
    final HeirarchicalLinkedListItem parent = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child1 = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child2 = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child3 = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child4 = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child5 = new HeirarchicalLinkedListItem();

    parent.addChildItem(child1);
    parent.addChildItem(child2);
    parent.addChildItem(child3);
    parent.addChildItem(child4);
    parent.addChildItem(child5);
    // child1 -> child2 -> child3 -> child4 -> child5

    child1.removeItem();
    // child2 -> child3 -> child4 -> child5
    assertNull("removed child should have null parent", child1.getParentItem());
    assertNull("removed child should have null previous", child1.getPreviousItem());
    assertNull("removed child should have null next", child1.getNextItem());
    assertEquals("first child should bd #2", child2, parent.getFirstChildItem());
    assertEquals("last child should be #5", child5, parent.getLastChildItem());
    assertNull("endpoint problem", child2.getPreviousItem());
    assertEquals("incorrect child order", child3, child2.getNextItem());
    assertEquals("incorrect child order", child2, child3.getPreviousItem());
    assertEquals("incorrect child order", child4, child3.getNextItem());
    assertEquals("incorrect child order", child3, child4.getPreviousItem());
    assertEquals("incorrect child order", child5, child4.getNextItem());
    assertEquals("incorrect child order", child4, child5.getPreviousItem());
    assertNull("endpoint problem", child5.getNextItem());

    child5.removeItem();
    // child2 -> child3 -> child4
    assertNull("removed child should have null parent", child5.getParentItem());
    assertNull("removed child should have null previous", child5.getPreviousItem());
    assertNull("removed child should have null next", child5.getNextItem());
    assertEquals("first child should bd #2", child2, parent.getFirstChildItem());
    assertEquals("last child should be #4", child4, parent.getLastChildItem());
    assertNull("endpoint problem", child2.getPreviousItem());
    assertEquals("incorrect child order", child3, child2.getNextItem());
    assertEquals("incorrect child order", child2, child3.getPreviousItem());
    assertEquals("incorrect child order", child4, child3.getNextItem());
    assertEquals("incorrect child order", child3, child4.getPreviousItem());
    assertNull("endpoint problem", child4.getNextItem());

    child3.removeItem();
    // child2 -> child4
    assertNull("removed child should have null parent", child3.getParentItem());
    assertNull("removed child should have null previous", child3.getPreviousItem());
    assertNull("removed child should have null next", child3.getNextItem());
    assertEquals("first child should bd #2", child2, parent.getFirstChildItem());
    assertEquals("last child should be #4", child4, parent.getLastChildItem());
    assertNull("endpoint problem", child2.getPreviousItem());
    assertEquals("incorrect child order", child4, child2.getNextItem());
    assertEquals("incorrect child order", child2, child4.getPreviousItem());
    assertNull("endpoint problem", child4.getNextItem());
  }

  /**
   * Tests the <code>getChildCount()</code> method
   */
  public void testGetChildCount() {
    final HeirarchicalLinkedListItem parent = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child1 = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child2 = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child3 = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child4 = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child5 = new HeirarchicalLinkedListItem();

    assertEquals("Empty parent should have 0 children", 0, parent.getChildCount());

    parent.addChildItem(child1);
    assertEquals(1, parent.getChildCount());
    assertEquals(0, child1.getChildCount());

    parent.addChildItem(child2);
    assertEquals(2, parent.getChildCount());
    assertEquals(0, child1.getChildCount());
    assertEquals(0, child2.getChildCount());

    parent.addChildItem(child3);
    assertEquals(3, parent.getChildCount());
    assertEquals(0, child1.getChildCount());
    assertEquals(0, child2.getChildCount());
    assertEquals(0, child3.getChildCount());

    child1.addChildItem(child4);
    assertEquals(3, parent.getChildCount());
    assertEquals(1, child1.getChildCount());
    assertEquals(0, child2.getChildCount());
    assertEquals(0, child3.getChildCount());
    assertEquals(0, child4.getChildCount());

    child1.addChildItem(child5);
    assertEquals(3, parent.getChildCount());
    assertEquals(2, child1.getChildCount());
    assertEquals(0, child2.getChildCount());
    assertEquals(0, child3.getChildCount());
    assertEquals(0, child4.getChildCount());
    assertEquals(0, child5.getChildCount());
  }

  /**
   * Tests the <code>getNextDepthFirstItem()</code> method. For this test,
   * we will consturct a HLL that look like the following:
   * <pre>
   *                           parent
   *                             |
   *                  child1<----+------------------->child2
   *                     |                              |
   *           child3<---+--->child4                  child5
   *                             |
   *                   child6<---+--->child7<------>child8
   * </pre>
   * So the order of the results of a depth-first search starting at the parent
   * should be:
   * <ol>
   * <li>child 1
   * <li>child 3
   * <li>child 4
   * <li>child 6
   * <li>child 7
   * <li>child 8
   * <li>child 2
   * <li>child 5
   * </ol>
   */
  @SuppressWarnings("nls")
  public void testGetNextDepthFirstItem() {
    // First, a node that as "not connected" should return null
    assertNull("A non-connected node should return null", new HeirarchicalLinkedListItem().getNextDepthFirstItem());

    // Setup the tree
    final HeirarchicalLinkedListItem parent = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child1 = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child2 = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child3 = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child4 = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child5 = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child6 = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child7 = new HeirarchicalLinkedListItem();
    final HeirarchicalLinkedListItem child8 = new HeirarchicalLinkedListItem();
    parent.addChildItem(child1);
    parent.addChildItem(child2);
    child1.addChildItem(child3);
    child1.addChildItem(child4);
    child4.addChildItem(child6);
    child4.addChildItem(child7);
    child4.addChildItem(child8);
    child2.addChildItem(child5);

    // Check the results
    assertEquals(child1, parent.getNextDepthFirstItem());
    assertEquals(child3, child1.getNextDepthFirstItem());
    assertEquals(child4, child3.getNextDepthFirstItem());
    assertEquals(child6, child4.getNextDepthFirstItem());
    assertEquals(child7, child6.getNextDepthFirstItem());
    assertEquals(child8, child7.getNextDepthFirstItem());
    assertEquals(child2, child8.getNextDepthFirstItem());
    assertEquals(child5, child2.getNextDepthFirstItem());
    assertNull(child5.getNextDepthFirstItem());
  }
}
