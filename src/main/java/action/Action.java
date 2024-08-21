/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author fjourda
 */
public abstract class Action {
    public Action() {
    }
public abstract void execute(HttpServletRequest request);
}