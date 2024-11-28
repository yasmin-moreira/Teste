import axios from 'https://cdn.jsdelivr.net/npm/axios@1.3.5/+esm'
import {id} from './app.js'
import { base_url } from './global.js'

export default {
    get: async () => {
        try {
            const data = await axios.get(`${base_url}/tarefas?id_user=`+id)
            return data
        } catch (err) {
            return err
        }
    },
    getById: async (id) => {
        try {
            const data = await axios.get(`${base_url}/tarefas/${id}`)
            return data
        } catch (err) {
            return err
        }
    },
    set: async (data) => {
        let nextId = await axios.get(`${base_url}/nextIdTarefas`)

        const result = await axios.post(`${base_url}/tarefas`, {
            "id": nextId.value,
            "name": data.name,
            "date": data.date,
            "recurrent": data.recurrent,
            "daysWeek": data.daysWeek,
            "priority": data.priority,
            "created_at": data.created_at,
            "status" : "aberto",
            "comment" : data.comment,
            "id_user" : id
        })

        await axios.patch(`${base_url}/nextIdTarefas/`, { value: nextId++ })
        
        location.reload();
        return result
    },
    change: async (id, data) => {
        await axios.patch(`${base_url}/tarefas/${id}`, data)
        location.reload();
    },
    delete: async (id) => {
        await axios.delete(`${base_url}/tarefas/${id}`)
        location.reload();
    }
}