/*
 * Copyright (c) 1997-1998 Borland International, Inc. All Rights Reserved.
 * 
 * This SOURCE CODE FILE, which has been provided by Borland as part
 * of a Borland product for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information of Borland.  
 *
 * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS 
 * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
 * THE PRODUCT.
 *
 * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD BORLAND, ITS RELATED
 * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
 * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
 * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
 * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
 * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
 * CODE FILE.
 */
//--------------------------------------------------------------------------------------------------
// Copyright (c) 1996-1998 Borland International, Inc. All Rights Reserved.
//--------------------------------------------------------------------------------------------------

package borland.jbcl.control;

import java.awt.*;
import java.awt.event.*;
import com.objectspace.jgl.Array;
import borland.jbcl.util.*;

public class FontChooser implements WindowListener, java.io.Serializable
{
  public static final int OK     = ButtonDialog.OK;
  public static final int CANCEL = ButtonDialog.CANCEL;

  public FontChooser(Frame frame, String title, Font value) {
    this.frame = frame;
    this.title = title;
    this.value = value;
    if (frame != null)
      dialog = new FontChooserDialog(frame, title, value);
  }

  public FontChooser(Frame frame, String title) {
    this(frame, title, null);
  }

  public FontChooser(Frame frame) {
    this(frame, "", null);
  }

  public FontChooser() {
    this(null, "", null);
  }

  public void setFrame(Frame frame) {
    this.frame = frame;
  }
  
  public Frame getFrame() {
    return frame;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getTitle() {
    return title;
  }
  
  public void setValue(Font value) {
    this.value = value;
    if (dialog != null)
      dialog.setValue(value);
  }

  public Font getValue() {
    if (dialog != null)
      value = dialog.getValue();
    return value;
  }

  public void setResult(int i) {
    result = i;
    if (dialog != null)
      dialog.setResult(result);
  }

  public int getResult() {
    if (dialog != null)
      result = dialog.getResult();
    return result;
  }

  public void show() {
    setVisible(true);
  }

  public void setVisible(boolean visible) {
    if (visible) {
      if (dialog == null) {
        if (frame == null)
          throw new IllegalStateException(Res.getString(Res.NoFrame));
        dialog = new FontChooserDialog(frame, title, value);
        dialog.setResult(result);
        dialog.addWindowListener(this);
        dialog.addActionListener(actionMulticaster);
      }
      dialog.setVisible(true);
    }
    else {
      if (dialog != null)
        dialog.setVisible(false);
    }
  }

  public boolean isVisible() {
    return dialog != null ? dialog.isVisible() : false;
  }

  public void addActionListener(ActionListener l) {
    actionMulticaster.add(l);
  }

  public void removeActionListener(ActionListener l) {
    actionMulticaster.remove(l);
  }

  public void windowOpened(WindowEvent e) {}
  public void windowClosing(WindowEvent e) {
    value = dialog.getValue();
    result = dialog.getResult();
  }
  public void windowClosed(WindowEvent e) {
    if (dialog != null) {
      value = dialog.getValue();
      result = dialog.getResult();
    }
    dialog = null;
    if (frame.isShowing()) {
      if (frame.getFocusOwner() != null)
        frame.getFocusOwner().requestFocus();
      else
        frame.requestFocus();
    }
  }
  public void windowIconified(WindowEvent e) {}
  public void windowDeiconified(WindowEvent e) {}
  public void windowActivated(WindowEvent e) {}
  public void windowDeactivated(WindowEvent e) {}

  protected FontChooserDialog dialog;
  protected Frame frame;
  protected String title;
  protected Font value;
  protected transient ActionMulticaster actionMulticaster = new ActionMulticaster();
  protected int result;
}
