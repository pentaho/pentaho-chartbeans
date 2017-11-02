/*!
* This program is free software; you can redistribute it and/or modify it under the
* terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
* Foundation.
*
* You should have received a copy of the GNU Lesser General Public License along with this
* program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
* or from the Free Software Foundation, Inc.,
* 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
*
* This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
* without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
* See the GNU Lesser General Public License for more details.
*
* Copyright (c) 2002-2017 Hitachi Vantara..  All rights reserved.
*/

package org.pentaho.util.collections;

public class HeirarchicalLinkedListItem implements Cloneable {

  /**
   * Holds the parent item for this item
   */
  private HeirarchicalLinkedListItem parent;

  /**
   * Holds a reference to the previous item in this level of the list. If this item is the first item
   * on this level of the heirarchy, this value will be <code>null</code>.
   */
  private HeirarchicalLinkedListItem prev;

  /**
   * Holds a reference to the next item in this level of the list. If this item is the last item on this
   * level of the heirarchy, this value will be <code>null</code>.
   */
  private HeirarchicalLinkedListItem next;

  /**
   * Holds a reference to the first child of this item. If this item has no children, both the <code>firstChild</code>
   * and the <code>lastChild</code> will be <code>null</code>.
   */
  private HeirarchicalLinkedListItem firstChild;

  /**
   * Holds a reference to the last child of this item. If this item has no children, both the <code>firstChild</code>
   * and the <code>lastChild</code> will be <code>null</code>.
   */
  private HeirarchicalLinkedListItem lastChild;

  /**
   * The number of times this item (or any of its children) have been modified.
   * This is most commonly used to determine if cached data has changed.
   */
  private long modNumber = 0L;

  /**
   * Returns the modification number for this item. This number can be used
   * to determine if a cache is invalid by comparing this number to the value when the
   * cache was last updated. If the number is different, it will indicate if this item
   * or any of it's children have been modified.
   */
  public long getModNumber() {
    return modNumber;
  }

  /**
   * Returns the parent item of this item. It will return <code>null</code> if this item has no parent.
   */
  public HeirarchicalLinkedListItem getParentItem() {
    return parent;
  }

  /**
   * Returns the item that is before this item at the same level of heirarchy. If this item is the first item at this
   * level in the heirarchy, this method will return <code>null<code>.
   */
  public HeirarchicalLinkedListItem getPreviousItem() {
    return prev;
  }

  /**
   * Returns the item that is after this item at the same level of heirarchy. If this item is the last item at this
   * level in the heirarchy, this method will return <code>null<code>.
   */
  public HeirarchicalLinkedListItem getNextItem() {
    return next;
  }

  /**
   * Returns ths first child of this item. If this item has no children, this method will return <code>null</code>.
   */
  public HeirarchicalLinkedListItem getFirstChildItem() {
    return firstChild;
  }

  /**
   * Returns the last child of this item. If this item has no children, this method will return <code>null</code>.
   */

  public HeirarchicalLinkedListItem getLastChildItem() {
    return lastChild;
  }

  /**
   * Removes this item from the heirarchy.
   */
  public void removeItem() {
    // Remove this item from the current heirarchy
    boolean modified = false;
    if (prev != null) {
      prev.next = next;
      modified = true;
    }
    if (next != null) {
      next.prev = prev;
      modified = true;
    }

    // Make sure to clean up the parent's first and last child
    if (parent != null) {
      if (this.equals(parent.firstChild)) {
        parent.firstChild = next;
      }
      if (this.equals(parent.lastChild)) {
        parent.lastChild = prev;
      }
      modified = true;
    }

    // If modified, mark as modified
    if (modified) {
      markModified();
    }

    // Cleanup this item (no dangling references)
    parent = null;
    prev = null;
    next = null;
  }

  /**
   * Returns the number of direct children the current element contains
   *
   * @return the number of direct children the current element contains
   */
  public int getChildCount() {
    int count = 0;
    for (HeirarchicalLinkedListItem item = firstChild; item != null; item = item.next) {
      ++count;
    }
    return count;
  }

  /**
   * Returns the next element in the heirarchical linked list based on a depth-first iteration
   *
   * @return the next depth-first item in the list, or <code>null</code> if this is the last item.
   */
  public HeirarchicalLinkedListItem getNextDepthFirstItem() {
    // If we have children, return the first child
    if (firstChild != null) {
      return firstChild;
    }

    // Since we have no children, the next item would be the "next" sibling
    if (next != null) {
      return next;
    }

    // We don't have any children or siblings ... we have to look for the first ancestor with a "next" sibling
    HeirarchicalLinkedListItem item = this;
    while (item != null && item.next == null) {
      item = item.parent;
    }

    // We are either out of ancestors or found an ancestor with a "next" sibling...
    return (item == null ? null : item.next);
  }

  /**
   * Clones this object. This class does not allow cloning of the data, so it will throw
   * a CloneNotSupportedException.
   */
  public Object clone() throws CloneNotSupportedException {
    // TODO: create a deep clone - new object will be root of new tree
    throw new CloneNotSupportedException();
  }

  /**
   * Adds a child item to this item. It will insert the item at the end of the list of children.
   *
   * @param newChild the item to be added at the end of the list.
   * @throws IllegalArgumentException indicates the supplied new child is <code>null</code>
   */
  protected void addChildItem(final HeirarchicalLinkedListItem newChild) throws IllegalArgumentException {
    addLastChildItem(newChild);
    markModified();
  }

  /**
   * Inserts a new child after the specified target element
   *
   * @param newChild the new item to add the list of child items
   * @param target   the reference item that will have the new item inserted after
   * @throws IllegalArgumentException indicates one of the supplied parameters is <code>null</code>
   * @throws IllegalStateException    indicates the new child already has a parent defined
   */
  protected void insertAfter(final HeirarchicalLinkedListItem newChild, final HeirarchicalLinkedListItem target)
      throws IllegalArgumentException, IllegalStateException {
    if (newChild == null || target == null) {
      throw new IllegalArgumentException();
    }

    // Find the child in the list of children
    final HeirarchicalLinkedListItem foundTarget = findChildItem(target);
    if (foundTarget == null) {
      throw new IllegalArgumentException("Could not find target child!"); // TODO: externalize
    }

    // Perform the insert
    foundTarget.addNextItem(newChild);
    if (lastChild.equals(target)) {
      lastChild = newChild;
    }

    // Mark this item as modified
    markModified();
  }

  /**
   * Inserts a new child before the specified target element
   *
   * @param newChild the new item to add the list of child items
   * @param target   the reference item that will have the new item inserted before
   * @throws IllegalArgumentException indicates one of the supplied parameters is <code>null</code>
   * @throws IllegalStateException    indicates the new child already has a parent defined
   */
  protected void insertBefore(final HeirarchicalLinkedListItem newChild, final HeirarchicalLinkedListItem target)
      throws IllegalArgumentException, IllegalStateException {
    if (newChild == null || target == null) {
      throw new IllegalArgumentException();
    }

    // Find the child in the list of children
    final HeirarchicalLinkedListItem foundTarget = findChildItem(target);
    if (foundTarget == null) {
      throw new IllegalArgumentException("Could not find target child!"); // TODO: externalize
    }

    // Perform the insert
    foundTarget.addPreviousItem(newChild);
    if (firstChild.equals(target)) {
      firstChild = newChild;
    }

    // Mark this item as modified
    markModified();
  }

  /**
   * Adds the child as the first child item in the list
   *
   * @param newChild the child item to be added
   * @throws IllegalArgumentException indicates the new child is <code>null</code>
   * @throws IllegalStateException    indicates the new child already has a parent defined
   */
  protected void addFirstChildItem(final HeirarchicalLinkedListItem newChild) throws IllegalArgumentException, IllegalStateException {
    if (firstChild == null) {
      addChildItem(newChild);
    } else {
      insertBefore(newChild, firstChild);
    }

    // Mark this item as modified
    markModified();
  }

  /**
   * Adds the child as the last child item in the list
   *
   * @param newChild the child item to be added
   * @throws IllegalArgumentException indicates the new child is <code>null</code>
   * @throws IllegalStateException    indicates the new child already has a parent defined
   */
  protected void addLastChildItem(final HeirarchicalLinkedListItem newChild) throws IllegalArgumentException, IllegalStateException {
    if (newChild == null) {
      throw new IllegalArgumentException();
    }

    if (lastChild != null) {
      insertAfter(newChild, lastChild);
    } else {
      // Insert the child manually
      newChild.setParentItem(this); // throws exception if the child already has a parent
      firstChild = newChild;
      lastChild = newChild;
      newChild.prev = null;
      newChild.next = null;
    }

    // Mark this item as modified
    markModified();
  }

  /**
   * Sets the parent of this item. If the item currently has a parent, this will cause an error.
   * <p/>
   * NOTE: this method is private but may be changed to protected if the need arises to override this method
   *
   * @throws IllegalArgumentException indicates the supplied new parent is <code>null</code>
   * @throws IllegalStateException    indicates this item already has a parent defined
   */
  private void setParentItem(final HeirarchicalLinkedListItem newParent) throws IllegalArgumentException, IllegalStateException {
    if (newParent == null) {
      throw new IllegalArgumentException();
    }

    // Do we have a parent (that isn't the same as the supplied parent)
    if (parent != null && !parent.equals(newParent)) {
      throw new IllegalStateException();
    }

    // Set the parent as a new parent
    parent = newParent;

    // Mark this item as modified
    markModified();
  }

  /**
   * Finds an item in the list of children. If the child can not be found, <code>null</code> will be returned.
   * <p/>
   * NOTE: this method is private but may be changed to protected if the need arises to override this method
   *
   * @param itemToFind the item to find in the list of children
   */
  private HeirarchicalLinkedListItem findChildItem(final HeirarchicalLinkedListItem itemToFind) {
    for (HeirarchicalLinkedListItem searchItem = firstChild; searchItem != null; searchItem = searchItem.next) {
      if (searchItem.equals(itemToFind)) {
        return searchItem;
      }
    }
    return null;
  }

  /**
   * Inserts a new item previous to the current item at the same level in the heirarchy.
   * NOTE: this method is private but may be changed to protected if the need arises to override this method
   *
   * @throws IllegalArgumentException indicates the supplied item is <code>null</code>
   * @throws IllegalStateException    indicates the new item already has a parent
   */
  private void addPreviousItem(final HeirarchicalLinkedListItem newItem) throws IllegalArgumentException, IllegalStateException {
    if (newItem == null) {
      throw new IllegalArgumentException();
    }

    // Update the new item
    newItem.setParentItem(parent); // Throws an exception if it already has a parent
    newItem.prev = prev;
    newItem.next = this;

    // If we already have a previous item, update it (it is getting a new "next" item)
    if (prev != null) {
      prev.next = newItem;
    }
    prev = newItem;

    // Mark this item as modified
    markModified();
  }

  /**
   * Inserts a new item after the current item at the same level in the heirarchy.
   * NOTE: this method is private but may be changed to protected if the need arises to override this method
   *
   * @throws IllegalArgumentException indicates the supplied item is <code>null</code>
   * @throws IllegalStateException    indicates the new item already has a parent
   */
  private void addNextItem(final HeirarchicalLinkedListItem newItem) throws IllegalArgumentException, IllegalStateException {
    if (newItem == null) {
      throw new IllegalArgumentException();
    }

    // Update the new item
    newItem.setParentItem(parent);
    newItem.prev = this;
    newItem.next = next;

    // If we already have a next item, update it (it is getting a new "prev" item) 
    if (next != null) {
      next.prev = newItem;
    }
    next = newItem;
    
    // Mark this item as modified
    markModified();
  }

  /**
   * Indicates that the item (or one of its children) has been modified.
   */
  protected void markModified() {
    ++modNumber;
    if (parent != null) {
      parent.markModified();
    }
  }
}
