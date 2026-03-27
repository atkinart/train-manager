const BASE_URL = import.meta.env.VITE_API_BASE_URL || ''

export async function fetchState() {
  const res = await fetch(`${BASE_URL}/api/v1/state`)
  if (!res.ok) throw new Error('Failed to load state')
  return res.json()
}

export async function manualSwitch({ nodeId, switchId, targetState }) {
  const res = await fetch(`${BASE_URL}/api/v1/switches/manual`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ nodeId, switchId, targetState })
  })
  if (!res.ok) throw new Error('Failed to send manual switch command')
  return res.json()
}

export function openEventStream(onMessage) {
  const es = new EventSource(`${BASE_URL}/api/v1/events/stream`)
  ;['heartbeat', 'rfid.detected', 'switch.command', 'switch.state'].forEach((eventName) => {
    es.addEventListener(eventName, (event) => {
      onMessage({ type: eventName, payload: JSON.parse(event.data) })
    })
  })
  return es
}
