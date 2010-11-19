package org.jggug.wiki

class SimpleWikiTagLib {
    static namespace = 'wiki'
    def wikiEngine
    def wikiContext

    def show = {attrs, body ->
        def content = body()
        def pageId = attrs.pageId?:"0";
        content=content.replaceAll(/\r\n|\r|\n/,"\n")
        if(wikiEngine){
            def text = wikiEngine.render(content.trim(),""+pageId, wikiContext)
            out<<text
        }else{
            out<<"(wiki engine ERROR)"
        }
    }

    /** breadcrumb */
    def topicPath = { attrs, body ->
        String url = attrs.url?:createLink(uri:'/')
        if(attrs.page) {
            out << "<ul id='crumbs'>"
            def parentList=parentLink(attrs.page).reverse()
            parentList.eachWithIndex{ title,idx ->
                if((idx+1)==parentList.size()) {
                    out << "<li>${title}</li>"
                } else {
                    out << "<li><a href='${url}display/${title}'>"
                    out << title
                    out << "</a></li>"
                }
            }
            out << "</ul>"
        }
    }

    def parentLink(page) {
        def ret=[]
        if(page) {
            ret << page.title?.encodeAsHTML()
            parentLink(page.page).each{ ret << it }
        }
        return ret
    }

}
