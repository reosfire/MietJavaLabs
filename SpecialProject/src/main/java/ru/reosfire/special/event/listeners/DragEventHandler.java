package ru.reosfire.special.event.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public interface DragEventHandler extends MouseMotionListener {
    @Override
    default void mouseMoved(MouseEvent e) {

    }
}
