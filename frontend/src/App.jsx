import { useEffect, useState } from 'react'
import { fetchState, manualSwitch, openEventStream } from './api/client'
import { Section } from './components/Section'

export function App() {
  const [state, setState] = useState({ nodes: [], readers: [], switches: [], recentEvents: [] })
  const [events, setEvents] = useState([])

  useEffect(() => {
    fetchState().then(setState).catch(console.error)
    const stream = openEventStream((msg) => {
      setEvents((prev) => [msg, ...prev].slice(0, 50))
      fetchState().then(setState).catch(console.error)
    })
    return () => stream.close()
  }, [])

  return (
    <main className="container">
      <h1>BRIO Train Manager</h1>
      <p className="subtitle">MVP monitoring and manual switch control skeleton</p>

      <div className="grid">
        <Section title="Nodes">
          <ul>{state.nodes.map((n) => <li key={n.id}>{n.id} — {n.status}</li>)}</ul>
        </Section>

        <Section title="Readers">
          <ul>{state.readers.map((r) => <li key={r.id}>{r.id} @ {r.nodeId}</li>)}</ul>
        </Section>

        <Section title="Switches">
          <ul>
            {state.switches.map((s) => (
              <li key={s.id}>
                {s.id} ({s.nodeId}) — <b>{s.state}</b>
                <button onClick={() => manualSwitch({ nodeId: s.nodeId, switchId: s.id, targetState: 'STRAIGHT' })}>Straight</button>
                <button onClick={() => manualSwitch({ nodeId: s.nodeId, switchId: s.id, targetState: 'DIVERGE' })}>Diverge</button>
              </li>
            ))}
          </ul>
        </Section>

        <Section title="Layout View (stub)">
          <div className="layoutStub">[track diagram placeholder]</div>
        </Section>

        <Section title="Live Event Log">
          <ul className="log">
            {events.map((e, idx) => <li key={idx}>{e.type}: {JSON.stringify(e.payload)}</li>)}
          </ul>
        </Section>
      </div>
    </main>
  )
}
