package cs_grails

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

//        "/"(view:"/teste/upload")
        "/" (redirect: "/teste/upload")
        "/**/relatorio"(redirect: "/relatorio")

        "/**/teste/upload/" (redirect: "/teste/upload")   //Vai para a pasta indicada pelo view sem retirar a pasta anterior
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
