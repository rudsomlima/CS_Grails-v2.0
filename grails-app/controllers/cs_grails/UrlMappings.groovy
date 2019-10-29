package cs_grails

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

//        "/"(view:"/teste/upload")
        "/" (redirect: "./teste/upload")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
