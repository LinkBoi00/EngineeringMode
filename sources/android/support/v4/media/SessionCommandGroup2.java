package android.support.v4.media;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class SessionCommandGroup2 {
    private static final String KEY_COMMANDS = "android.media.mediasession2.commandgroup.commands";
    private static final String PREFIX_COMMAND_CODE = "COMMAND_CODE_";
    private static final String PREFIX_COMMAND_CODE_PLAYBACK = "COMMAND_CODE_PLAYBACK_";
    private static final String PREFIX_COMMAND_CODE_PLAYLIST = "COMMAND_CODE_PLAYLIST_";
    private static final String PREFIX_COMMAND_CODE_VOLUME = "COMMAND_CODE_VOLUME_";
    private static final String TAG = "SessionCommandGroup2";
    private Set<SessionCommand2> mCommands;

    public SessionCommandGroup2() {
        this.mCommands = new HashSet();
    }

    public SessionCommandGroup2(SessionCommandGroup2 other) {
        HashSet hashSet = new HashSet();
        this.mCommands = hashSet;
        if (other != null) {
            hashSet.addAll(other.mCommands);
        }
    }

    public void addCommand(SessionCommand2 command) {
        if (command != null) {
            this.mCommands.add(command);
            return;
        }
        throw new IllegalArgumentException("command shouldn't be null");
    }

    public void addCommand(int commandCode) {
        if (commandCode != 0) {
            this.mCommands.add(new SessionCommand2(commandCode));
            return;
        }
        throw new IllegalArgumentException("command shouldn't be null");
    }

    public void addAllPredefinedCommands() {
        addCommandsWithPrefix(PREFIX_COMMAND_CODE);
    }

    /* access modifiers changed from: package-private */
    public void addAllPlaybackCommands() {
        addCommandsWithPrefix(PREFIX_COMMAND_CODE_PLAYBACK);
    }

    /* access modifiers changed from: package-private */
    public void addAllPlaylistCommands() {
        addCommandsWithPrefix(PREFIX_COMMAND_CODE_PLAYLIST);
    }

    /* access modifiers changed from: package-private */
    public void addAllVolumeCommands() {
        addCommandsWithPrefix(PREFIX_COMMAND_CODE_VOLUME);
    }

    private void addCommandsWithPrefix(String prefix) {
        Field[] fields = SessionCommand2.class.getFields();
        if (fields != null) {
            for (int i = 0; i < fields.length; i++) {
                if (fields[i].getName().startsWith(prefix) && !fields[i].getName().equals("COMMAND_CODE_CUSTOM")) {
                    try {
                        this.mCommands.add(new SessionCommand2(fields[i].getInt((Object) null)));
                    } catch (IllegalAccessException e) {
                        Log.w(TAG, "Unexpected " + fields[i] + " in MediaSession2");
                    }
                }
            }
        }
    }

    public void removeCommand(SessionCommand2 command) {
        if (command != null) {
            this.mCommands.remove(command);
            return;
        }
        throw new IllegalArgumentException("command shouldn't be null");
    }

    public void removeCommand(int commandCode) {
        if (commandCode != 0) {
            this.mCommands.remove(new SessionCommand2(commandCode));
            return;
        }
        throw new IllegalArgumentException("commandCode shouldn't be COMMAND_CODE_CUSTOM");
    }

    public boolean hasCommand(SessionCommand2 command) {
        if (command != null) {
            return this.mCommands.contains(command);
        }
        throw new IllegalArgumentException("command shouldn't be null");
    }

    public boolean hasCommand(int commandCode) {
        if (commandCode != 0) {
            for (SessionCommand2 command : this.mCommands) {
                if (command.getCommandCode() == commandCode) {
                    return true;
                }
            }
            return false;
        }
        throw new IllegalArgumentException("Use hasCommand(Command) for custom command");
    }

    public Set<SessionCommand2> getCommands() {
        return new HashSet(this.mCommands);
    }

    public Bundle toBundle() {
        ArrayList<Bundle> list = new ArrayList<>();
        for (SessionCommand2 command : this.mCommands) {
            list.add(command.toBundle());
        }
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_COMMANDS, list);
        return bundle;
    }

    public static SessionCommandGroup2 fromBundle(Bundle commands) {
        List<Parcelable> list;
        SessionCommand2 command;
        if (commands == null || (list = commands.getParcelableArrayList(KEY_COMMANDS)) == null) {
            return null;
        }
        SessionCommandGroup2 commandGroup = new SessionCommandGroup2();
        for (int i = 0; i < list.size(); i++) {
            Parcelable parcelable = list.get(i);
            if ((parcelable instanceof Bundle) && (command = SessionCommand2.fromBundle((Bundle) parcelable)) != null) {
                commandGroup.addCommand(command);
            }
        }
        return commandGroup;
    }
}
