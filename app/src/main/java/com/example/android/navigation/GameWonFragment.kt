/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.core.app.ShareCompat
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameWonBinding
import android.content.pm.ResolveInfo
import android.content.pm.PackageManager



class GameWonFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_won, container, false)
        binding.nextMatchButton.setOnClickListener { view: View ->
            view.findNavController().navigate(
                    GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
        }
        /**
         * The setHsOptionsMenu method tells android that this fragment
         * has an options menu, in which case it is going to call the
         * onCreateOptionsMenu defined as a method below
         */
        setHasOptionsMenu(true)

        return binding.root
    }

    /**
     * @method getShareIntent
     * @Description uses ShareCompat to create an intent
     * that will share a message that contains. The ShareCompat api is
     * specifically for creating intents that will share content like emails,
     * messages, posts, images etc.
     * @return The created intent
     */
    private fun getShareIntent() : Intent {
        val args = GameWonFragmentArgs.fromBundle(requireArguments())
        return ShareCompat.IntentBuilder.from(activity!!)
                .setText(getString(R.string.share_success_text, args.numCorrect, args.numQuestions))
                .setType("text/plain")
                .intent
    }

    /**
     * @method shareSuccess
     * @description uses the startActivity method to launh the
     * intent created by the shareCompat api.
     * @Return void
     */
    private fun shareSuccess() {
        startActivity(getShareIntent())
    }

    /**
     * @method onCreateOptionsMenu
     * @Description inflates the specified menu, in this case it is a
     * social share button
     * @param {Menu} menu
     * @param {MenuInflater} inflater
     *
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.winner_menu, menu)

        /**
         * The resolveActivity method checks whether there are any
         * apps that can actually handle the content to be shared
         * If no apps are found, then it sets the visibility of the
         * Share menu to false.
         *
         * It does so by checking with the packageManager which is aware of
         * every activity that is registered in the android manifest of every
         * application
         */
        if (null == getShareIntent().resolveActivity(requireActivity().packageManager)) {
            // hide the menu item if it doesn't resolve
            menu.findItem(R.id.share)?.isVisible = false
        }
    }

    /**
     * @method onOptionsItemSelected
     * @description handles item selection on menu. When the share
     * button is pressed it will call the shareSuccess method which
     * will share the intent that has been created
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
    }
}
