package org.example.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "henrik-api", url = $$"${henrik.base-url}")
interface HenrikApiClient {
    @GetMapping("/valorant/v1/stored-matches/{region}/{name}/{tag}")
    fun getStoredMatches(
        @PathVariable region: String,
        @PathVariable name: String,
        @PathVariable tag: String,
        @RequestParam(value = "size", required = false) size: Int? = null,
        @RequestParam(value = "page", required = false) page: Int? = null
    ): StoredMatchesResponse

    @GetMapping("/valorant/v3/mmr/{region}/pc/{name}/{tag}")
    fun getMmr(
        @PathVariable region: String,
        @PathVariable name: String,
        @PathVariable tag: String
    ): MmrResponse
}
