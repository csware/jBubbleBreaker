/*
 * Copyright 2010 Sven Strickroth <email@cs-ware.de>
 * 
 * This file is part of jBubbleBreaker.
 * 
 * jBubbleBreaker is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 as
 * published by the Free Software Foundation.
 * 
 * jBubbleBreaker is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with jBubbleBreaker. If not, see <http://www.gnu.org/licenses/>.
 */

package org.jbubblebreaker;

/**
 * GameLifeCycleObserver, called from Game to inform GUI about Game-Lifecycle
 * @author Sven Strickroth
 */
public interface GameLifecycleObserverIf {
	/**
	 * Redoable move taken
	 */
	public void firstMoveTaken();

	/**
	 * Game over
	 */
	public void gameOver();
}
