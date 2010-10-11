package org.jggug.wiki

import net.sf.ehcache.Ehcache
import net.sf.ehcache.Element

class CacheService {

    static transactional = false

    Ehcache contentCache
    Ehcache wikiCache

    def getContent(key) {  
        contentCache.get(key)?.getValue()
    }

    def putContent(key,value) {
        def old = getContent(key)
        contentCache.put(new Element(key,value))
        return old
    }

    def removeContent(key) {
        contentCache.remove key
    }
                    
    def getWikiText(key) {
        wikiCache.get(key)?.getValue()
    }

    def removeWikiText(key) {
        wikiCache.remove key
    }

    def putWikiText(key,value) {
        def old = getWikiText(key)
        wikiCache.put(new Element(key,value))
        return old
    }
}