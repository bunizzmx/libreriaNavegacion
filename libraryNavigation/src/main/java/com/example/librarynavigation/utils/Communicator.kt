package com.example.librarynavigation.utils

interface Communicator {
    fun changeFragment(titleLabel: String, visibility: Boolean)
    fun backfragment( )
    fun changeFragmentProgress(titleLabel: String, visibility: Boolean, part: Int, total: Int)
}