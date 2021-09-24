package me.lunaluna.fabric.elytrarecast

import net.minecraft.client.network.ClientPlayerEntity
import net.minecraft.entity.EquipmentSlot
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.ElytraItem
import net.minecraft.item.Items
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket

class ElytraHelper(private val player: ClientPlayerEntity) {
    fun castElytra() = if (checkElytra() && checkFallFlyingIgnoreGround()) {
        player.networkHandler.sendPacket(
            ClientCommandC2SPacket(player, ClientCommandC2SPacket.Mode.START_FALL_FLYING)
        )
        true
    } else false

    private fun checkElytra() = if (player.input.jumping && !player.abilities.flying && !player.hasVehicle() && !player.isClimbing) {
        val itemStack = player.getEquippedStack(EquipmentSlot.CHEST)
        itemStack.isOf(Items.ELYTRA) && ElytraItem.isUsable(itemStack)
    } else false

    private fun checkFallFlyingIgnoreGround() = if (!player.isTouchingWater && !player.hasStatusEffect(StatusEffects.LEVITATION)) {
        val itemStack = player.getEquippedStack(EquipmentSlot.CHEST)
        if (itemStack.isOf(Items.ELYTRA) && ElytraItem.isUsable(itemStack)) {
            player.startFallFlying()
            true
        } else false
    } else false
}