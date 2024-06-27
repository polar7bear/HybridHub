package com.pharmago.PharmaGo.api.service

import spock.lang.Specification

import java.nio.charset.StandardCharsets

class UriBuilderServiceTest extends Specification {

    private UriBuilderService uriBuilderService

    // setup() 은 Junit5의 @BeforeEach와 유사
    def setup() {
        uriBuilderService = new UriBuilderService()
    }

    def "uriBuild - 한글 파라미터의 경우 정상적으로 인코딩이 되어야 함"() {
        given:
        String address = "고양시 일산서구"
        def charset = StandardCharsets.UTF_8

        when:
        def uri = uriBuilderService.buildUri(address) // def 는 동적 변수 할당으로 var와 비슷한 개념
        def decoded = URLDecoder.decode(uri.toString(), charset)

        then:
        println uri
        decoded == "https://dapi.kakao.com/v2/local/search/address.json?query=고양시 일산서구"
    }
}
