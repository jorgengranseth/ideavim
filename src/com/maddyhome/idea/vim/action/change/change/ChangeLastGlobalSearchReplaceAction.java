/*
 * IdeaVim - Vim emulator for IDEs based on the IntelliJ platform
 * Copyright (C) 2003-2019 The IdeaVim authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.maddyhome.idea.vim.action.change.change;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.maddyhome.idea.vim.VimPlugin;
import com.maddyhome.idea.vim.action.VimCommandAction;
import com.maddyhome.idea.vim.command.Argument;
import com.maddyhome.idea.vim.command.Command;
import com.maddyhome.idea.vim.command.MappingMode;
import com.maddyhome.idea.vim.ex.LineRange;
import com.maddyhome.idea.vim.handler.ChangeEditorActionHandler;
import com.maddyhome.idea.vim.handler.VimActionHandler;
import com.maddyhome.idea.vim.helper.EditorHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;
import java.util.Set;


public class ChangeLastGlobalSearchReplaceAction extends VimCommandAction {
  @NotNull
  @Override
  public Set<MappingMode> getMappingModes() {
    return MappingMode.N;
  }

  @NotNull
  @Override
  public Set<List<KeyStroke>> getKeyStrokesSet() {
    return parseKeysSet("g&");
  }

  @NotNull
  @Override
  public Command.Type getType() {
    return Command.Type.OTHER_SELF_SYNCHRONIZED;
  }

  @NotNull
  @Override
  protected VimActionHandler makeActionHandler() {
    return new ChangeEditorActionHandler.SingleExecution() {
      @Override
      public boolean execute(@NotNull Editor editor,
                             @NotNull DataContext context,
                             int count,
                             int rawCount,
                             @Nullable Argument argument) {
        final LineRange range = new LineRange(0, EditorHelper.getLineCount(editor) - 1);
        return VimPlugin.getSearch()
          .searchAndReplace(editor, editor.getCaretModel().getPrimaryCaret(), range, "s", "//~/&");
      }
    };
  }
}
