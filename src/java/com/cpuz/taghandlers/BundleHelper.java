/*
 * LocaleHelper.java
 *
 * Created on March 11, 2004, 3:39 PM
 */

package com.cpuz.taghandlers;

import com.cpuz.domain.UserType;
import com.cpuz.tools.Const;
import java.util.Enumeration; 
import java.util.Locale; 
import java.util.ResourceBundle;  

/**
 *
 * @author  avk
 */
public class BundleHelper {
    
    /** Creates a new instance of LocaleHelper */
    private BundleHelper() {
    }
    
    public static ResourceBundle getBundle(Enumeration locs) { 
        
        if(locs == null)
            return ResourceBundle.getBundle(Const.RESOURCE_BOUNDLE_MULTILANG);
 
        Locale loc, loc2; 
        ResourceBundle bundle = null; 
        while(locs.hasMoreElements()) { 
            loc = (Locale)(locs.nextElement()); 
      
            bundle = 
                ResourceBundle.getBundle(Const.RESOURCE_BOUNDLE_MULTILANG,loc);
            loc2 = bundle.getLocale(); 
            
            // This is not quite equal to the JSTL algorithm
            if(loc2.equals(loc)) return bundle; 
            else if(loc.getLanguage() == loc2.getLanguage())
                return bundle; 
            
        } 
        return ResourceBundle.getBundle(Const.RESOURCE_BOUNDLE_MULTILANG);
    }
    
}
